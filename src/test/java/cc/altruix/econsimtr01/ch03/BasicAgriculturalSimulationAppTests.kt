/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.ITimeProvider
import cc.altruix.econsimtr01.mock
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by pisarenko on 18.05.2016.
 */
class BasicAgriculturalSimulationAppTests {
    @Test
    fun composeTargetFileName() {
        // Prepare
        val timeProvider = mock<ITimeProvider>()
        Mockito.`when`(timeProvider.now()).thenReturn(DateTime(2016, 5, 18,
                6, 26))
        val out = BasicAgriculturalSimulationApp(
                cmdLineParamValidator = CmdLineParametersValidator(),
                timeProvider = timeProvider
        )
        // Run method under test
        val res = out.composeTargetFileName()
        // Verify
        Assertions.assertThat(res).isEqualToIgnoringCase("agriculture-0.csv")
    }
}