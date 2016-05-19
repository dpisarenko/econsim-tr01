/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 */

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.shouldBe
import cc.altruix.econsimtr01.toPrologTheory
import org.junit.Test

/**
 * Created by pisarenko on 21.04.2016.
 */
class PercentageResourceLevelColFunctionTests {
    @Test
    fun invoke() {
        val out = PercentageResourceLevelColFunction("stacy", "r14", 480.0)
        invokeTestLogic(out, "0.0", "0.0")
        invokeTestLogic(out, "240.0", "50.0")
        invokeTestLogic(out, "480.0", "100.0")
    }
    private fun invokeTestLogic(out: PercentageResourceLevelColFunction,
                                amount:String,
                                expectedResult:String) {
        out.invoke("resourceLevel(86400, 'stacy', 'r14', $amount).".toPrologTheory(),
                86400L).shouldBe(expectedResult)
    }
}