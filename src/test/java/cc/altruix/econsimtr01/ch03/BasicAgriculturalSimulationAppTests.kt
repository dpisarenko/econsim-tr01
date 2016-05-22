/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
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
                cmdLineParamValidator = AgrigulturalSimulationCmdLineParametersValidator(),
                timeProvider = timeProvider,
                targetDir = "home"
        )
        // Run method under test
        val res = out.composeTargetFileName("agriculture")
        // Verify
        Assertions.assertThat(res).isEqualToIgnoringCase(
                "home/agriculture-1463541960000.csv"
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
        val actualFileName=
                "src/test/resources/ch03/agriculture-1463541960000.csv"
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
                "BasicAgriculturalSimulationRye.properties",
                "src/test/resources/ch03/"+
                "BasicAgriculturalSimulationWheat.properties"),
                System.out,
                System.err)
        assertFilesEqual(actualFile,
                File("src/test/resources/ch03/agriculture-1463541960000" +
                        ".expected.csv"))
     }
}
