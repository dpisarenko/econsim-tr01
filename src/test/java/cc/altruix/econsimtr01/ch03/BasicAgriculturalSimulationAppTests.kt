/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.ITimeProvider
import cc.altruix.econsimtr01.assertFilesEqual
import cc.altruix.econsimtr01.mock
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito
import java.io.File

/**
 * Created by pisarenko on 18.05.2016.
 */
class BasicAgriculturalSimulationAppTests {
    @Test
    fun composeTargetFileName() {
        // Prepare
        val timeProvider = mockTimeProvider()
        val out = BasicAgriculturalSimulationApp(
                cmdLineParamValidator = CmdLineParametersValidator(),
                timeProvider = timeProvider
        )
        // Run method under test
        val res = out.composeTargetFileName()
        // Verify
        Assertions.assertThat(res).isEqualToIgnoringCase(
                "agriculture-1463541960000.csv"
        )
    }

    private fun mockTimeProvider(): ITimeProvider {
        val timeProvider = mock<ITimeProvider>()
        Mockito.`when`(timeProvider.now()).thenReturn(DateTime(2016, 5, 18,
                6, 26))
        return timeProvider
    }

    @Test
    fun integrationTest() {
        val actualFileName="src/test/resources/ch03/agriculture-1463541960000.csv"
        val actualFile = File(actualFileName)
        if (actualFile.exists()) {
            actualFile.delete()
        }
        val timeProvider = mockTimeProvider()
        val out = BasicAgriculturalSimulationApp(
                timeProvider = timeProvider,
                targetDir =
                "src/test/resources/ch03/"
        )
        out.run(arrayOf("src/test/resources/ch03/"+
                "BasicAgriculturalSimulationRye.properties", "src/test/resources/ch03/"+
                "BasicAgriculturalSimulationRye.properties"),
                System.out,
                System.err)
        assertFilesEqual(actualFile,
                File("src/test/resources/ch03/agriculture-1463541960000" +
                        ".expected.csv"))
     }
}