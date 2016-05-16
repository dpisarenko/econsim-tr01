/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.util.*

/**
 * Created by pisarenko on 16.05.2016.
 */
class EnoughCapacityForHarvestingTests {
    @Test
    fun validateWiring() {
        validateWirintTestLogic(
                requiredEffort = 1.0,
                availableTime = 2.0,
                expectedValidity = true,
                expectedMessage = ""
        )
        validateWirintTestLogic(
                requiredEffort = 2.0,
                availableTime = 1.0,
                expectedValidity = false,
                expectedMessage = "Process 3: It requires 2.0 hours of " +
                        "effort, but only 1.0 is available"
        )
    }

    private fun validateWirintTestLogic(requiredEffort: Double,
                                        availableTime: Double,
                                        expectedValidity: Boolean,
                                        expectedMessage: String) {
        // Prepare
        val data = Properties()
        val scenario =
                PropertiesFileSimParametersProviderWithPredefinedData(data)
        val out = Mockito.spy(EnoughCapacityForHarvesting())
        Mockito.doReturn(requiredEffort).`when`(out)
                .calculateRequiredEffort(scenario)
        Mockito.doReturn(availableTime).`when`(out)
                .calculateAvailableTime(scenario)
        // Run method under test
        val res = out.validate(scenario)
        // Verify
        Assertions.assertThat(res.message).isEqualTo(expectedMessage)
        Assertions.assertThat(res.valid).isEqualTo(expectedValidity)
    }
}