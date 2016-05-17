/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test
import java.util.*

/**
 * Created by pisarenko on 17.05.2016.
 */
class Process2Tests {
    @Test
    fun timeToRun() {
        timeToRunTestLogic(
                time = DateTime(0, 7, 4, 23, 59),
                expectedResult = false
        )
        timeToRunTestLogic(
                time = DateTime(0, 7, 5, 0, 0),
                expectedResult = true
        )
        timeToRunTestLogic(
                time = DateTime(0, 7, 5, 0, 1),
                expectedResult = false
        )
    }

    private fun timeToRunTestLogic(time: DateTime,
                                   expectedResult: Boolean) {
        // Prepare
        val data = Properties()
        data["Process2End"] = "05.07"
        val simParamProv =
                AgriculturalSimParametersProviderWithPredefinedData(data)
        simParamProv.initAndValidate()
        val out = Process2(simParamProv)
        // Run method under test
        val res = out.timeToRun(time)
        // Verify
        Assertions.assertThat(res).isEqualTo(expectedResult)
    }
}