/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import org.junit.Test

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
        // TODO: Continue here
// TODO: Implement this
// TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}