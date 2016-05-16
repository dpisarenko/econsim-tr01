/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * Created by pisarenko on 16.05.2016.
 */
class EnoughCapacityForHarvestingTests {
    @Test
    fun validateWiring() {
        validateWiringTestLogic(
                requiredEffort = 1.0,
                availableTime = 2.0,
                expectedValidity = true,
                expectedMessage = ""
        )
        validateWiringTestLogic(
                requiredEffort = 2.0,
                availableTime = 1.0,
                expectedValidity = false,
                expectedMessage = "Process 3: It requires 2.0 hours of " +
                        "effort, but only 1.0 is available"
        )
    }
    @Test
    fun calculateRequiredEffort() {
        // Prepare
        val data = Properties()
        data["SizeOfField"] = "250000"
        data["Process3EffortPerSquareMeter"] = "0.45"
        val scenario =
                PropertiesFileSimParametersProviderWithPredefinedData(data)
        scenario.initAndValidate()
        val out = EnoughCapacityForHarvesting()
        // Run method under test
        val res = out.calculateRequiredEffort(scenario)
        // Verify
        Assertions.assertThat(res).isEqualTo(250000.0 * 0.45)
    }

    private fun validateWiringTestLogic(requiredEffort: Double,
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