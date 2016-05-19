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
class MoneyInSavingsAccountColFunctionTests {
    @Test
    fun invoke() {
        val out = MoneyInSavingsAccountColFunction()
        out.invoke(
                "resourceLevel(123, savingsAccount, r2, 210.23).".toPrologTheory(),
                123L)
                .shouldBe("210.23")
    }
}