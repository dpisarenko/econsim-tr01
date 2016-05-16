/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.junit.Test
import java.util.*

/**
 * Created by pisarenko on 16.05.2016.
 */
class OneDateBeforeOtherValidatorTests {
    @Test
    fun validate() {
        validateTestLogic("05.07", "10.08", true, "")
        validateTestLogic("10.08", "05.07", false, "Process2End " +
                "('10.08') must be before " +
                "Process3End ('05.07')")
    }

    private fun validateTestLogic(earlierDate: String,
                                  laterDate: String,
                                  expectedValidity: Boolean,
                                  expectedMessage: String) {
        // Prepare
        val data = Properties()
        data["Process2End"] = earlierDate
        data["Process3End"] = laterDate
        val scenario =
                PropertiesFileSimParametersProviderWithPredefinedData(data)
        scenario.initAndValidate()
        val out = OneDateBeforeOtherValidator("Process2End", "Process3End")
        // Run method under test
        val res = out.validate(scenario)
        // Verify
        Assertions.assertThat(res.message).isEqualTo(expectedMessage)
        Assertions.assertThat(res.valid).isEqualTo(expectedValidity)
    }
}