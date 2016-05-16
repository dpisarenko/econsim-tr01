/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.shouldBe
import cc.altruix.econsimtr01.shouldBeFalse
import cc.altruix.econsimtr01.shouldBeTrue
import cc.altruix.econsimtr01.toPrologTheory
import org.junit.Test

/**
 * Created by pisarenko on 21.04.2016.
 */
class TransitionReadinessColFunctionTests {
    @Test
    fun softwareComplete() {
        val out = TransitionReadinessColFunction()
        out.softwareComplete("resourceLevel(86400, 'stacy', 'r14', 0.0).".toPrologTheory(),
                86400L).shouldBeFalse()
        out.softwareComplete("resourceLevel(86400, 'stacy', 'r14', 479.0).".toPrologTheory(),
                86400L).shouldBeFalse()
        out.softwareComplete("resourceLevel(86400, 'stacy', 'r14', 480.0).".toPrologTheory(),
                86400L).shouldBeTrue()
        out.softwareComplete("resourceLevel(86400, 'stacy', 'r14', 481.0).".toPrologTheory(),
                86400L).shouldBeTrue()

    }
    @Test
    fun enoughPeopleInList() {
        val out = TransitionReadinessColFunction()
        out.enoughPeopleInList("resourceLevel(86400, list, 'r06-pc1', 0.0).".toPrologTheory(), 86400L).shouldBeFalse()
        out.enoughPeopleInList("resourceLevel(86400, list, 'r06-pc1', 999.0).".toPrologTheory(), 86400L).shouldBeFalse()
        out.enoughPeopleInList("resourceLevel(86400, list, 'r06-pc1', 1000.0).".toPrologTheory(), 86400L).shouldBeTrue()
        out.enoughPeopleInList("resourceLevel(86400, list, 'r06-pc1', 1001.0).".toPrologTheory(), 86400L).shouldBeTrue()
    }
    @Test
    fun enoughMoney() {
        val out = TransitionReadinessColFunction()
        out.enoughMoney("resourceLevel(86400, savingsAccount, r2, 0.0).".toPrologTheory(), 86400L).shouldBeFalse()
        out.enoughMoney("resourceLevel(86400, savingsAccount, r2, 2999.0).".toPrologTheory(), 86400L).shouldBeFalse()
        out.enoughMoney("resourceLevel(86400, savingsAccount, r2, 3000.0).".toPrologTheory(), 86400L).shouldBeTrue()
        out.enoughMoney("resourceLevel(86400, savingsAccount, r2, 3000.1).".toPrologTheory(), 86400L).shouldBeTrue()
    }
    @Test
    fun invoke() {
        val out = TransitionReadinessColFunction()
        val th1 =
        """
        resourceLevel(86400, 'stacy', 'r14', 480.0).
        resourceLevel(86400, list, 'r06-pc1', 1000.0).
        resourceLevel(86400, savingsAccount, r2, 3000.0).
        """
        val th2 =
        """
        resourceLevel(86400, 'stacy', 'r14', 479.0).
        resourceLevel(86400, list, 'r06-pc1', 1000.0).
        resourceLevel(86400, savingsAccount, r2, 3000.0).
        """
        val th3 =
                """
        resourceLevel(86400, 'stacy', 'r14', 480.0).
        resourceLevel(86400, list, 'r06-pc1', 999.0).
        resourceLevel(86400, savingsAccount, r2, 3000.0).
        """
        val th4 =
                """
        resourceLevel(86400, 'stacy', 'r14', 480.0).
        resourceLevel(86400, list, 'r06-pc1', 1000.0).
        resourceLevel(86400, savingsAccount, r2, 2999.99).
        """

        out.invoke(th1.toPrologTheory(), 86400L).shouldBe("yes")

        arrayOf(th2, th3, th4)
                .map {it.toPrologTheory()}
                .forEach { out.invoke(it, 86400L).shouldBe("no") }
    }
}