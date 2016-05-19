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