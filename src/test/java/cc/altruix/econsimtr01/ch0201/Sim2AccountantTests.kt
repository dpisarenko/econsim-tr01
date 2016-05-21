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

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.PlResource
import cc.altruix.econsimtr01.shouldBe
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Sim2AccountantTests {
    @Test
    fun logStockLevelsCallsLogCohortData() {
        val out = createSim2Accountant()
        Mockito.doNothing().`when`(out).logNormalStockLevels(1L)
        Mockito.doNothing().`when`(out).logCohortData(1L)
        // Run method under test
        out.logStockLevels(1L)
        // Verify
        Mockito.verify(out).logNormalStockLevels(1L)
        Mockito.verify(out).logCohortData(1L)
    }

    protected fun createSim2Accountant(): Sim2Accountant {
        val logTarget = StringBuilder()
        val agents = emptyList<IAgent>()
        val resources = emptyList<PlResource>()
        val out = Mockito.spy(Sim2Accountant(logTarget,
                agents,
                resources))
        return out
    }

    @Test
    fun createSubscribersCountByNumberOfInteractions() {
        val out = createSim2Accountant()
        val act = out.createSubscribersCountByNumberOfInteractions()
        act.size.shouldBe(7)
        Assertions.assertThat(act.get(0)).isNull()
        Assertions.assertThat(act.get(1)?.get()).isEqualTo(0)
        Assertions.assertThat(act.get(2)?.get()).isEqualTo(0)
        Assertions.assertThat(act.get(3)?.get()).isEqualTo(0)
        Assertions.assertThat(act.get(4)?.get()).isEqualTo(0)
        Assertions.assertThat(act.get(5)?.get()).isEqualTo(0)
        Assertions.assertThat(act.get(6)?.get()).isEqualTo(0)
        Assertions.assertThat(act.get(7)?.get()).isEqualTo(0)
    }
    @Test
    fun logCohortData() {
        val out = createSim2Accountant()
        val list = ListAgent("list")
        Mockito.doReturn(list).`when`(out).findList()
        val subscribersCountByNumberOfInteractions =
                HashMap<Int, AtomicInteger>()
        Mockito.doReturn(subscribersCountByNumberOfInteractions)
            .`when`(out).calculateSubscribersCountByNumberOfInteractions(list)
        val time = 1L
        Mockito.doNothing().`when`(out).logSubscribersCountByNumberOfInteractions(list,
                subscribersCountByNumberOfInteractions,
                time, out.logTarget)
        // Run method under test
        out.logCohortData(time)
        // Verify
        Mockito.verify(out).findList()
        Mockito.verify(out).calculateSubscribersCountByNumberOfInteractions(list)
        Mockito.verify(out).logSubscribersCountByNumberOfInteractions(list,
                subscribersCountByNumberOfInteractions,
                time, out.logTarget)

    }
    @Test
    fun logSubscribersCountByNumberOfInteractions() {
        val out = createSim2Accountant()
        val subscribersCountByNumberOfInteractions =
                hashMapOf(
                        Pair(1, AtomicInteger(10)),
                        Pair(2, AtomicInteger(20)),
                        Pair(3, AtomicInteger(30)),
                        Pair(4, AtomicInteger(40)),
                        Pair(5, AtomicInteger(50)),
                        Pair(6, AtomicInteger(60)),
                        Pair(7, AtomicInteger(70))
                )
        val log = StringBuilder()
        val list = ListAgent("list")
        // Run method under test
        out.logSubscribersCountByNumberOfInteractions(list,
                subscribersCountByNumberOfInteractions,
                1L,
                log)
        // Verify
        var nl = System.lineSeparator()
        log.toString().shouldBe("""resourceLevel(1, 'list', 'r06-pc1', 10).
resourceLevel(1, 'list', 'r07-pc2', 20).
resourceLevel(1, 'list', 'r08-pc3', 30).
resourceLevel(1, 'list', 'r09-pc4', 40).
resourceLevel(1, 'list', 'r10-pc5', 50).
resourceLevel(1, 'list', 'r11-pc6', 60).
resourceLevel(1, 'list', 'r12-pc7', 70).
""")
    }
    @Test
    fun calculateSubscribersCountByNumberOfInteractions() {
        val out = createSim2Accountant()
        val list = ListAgent("list")
        val id = AtomicInteger(1)
        addSubscribers(id, list, 10, 1)
        addSubscribers(id, list, 11, 2)
        addSubscribers(id, list, 12, 3)
        addSubscribers(id, list, 13, 4)
        addSubscribers(id, list, 14, 5)
        addSubscribers(id, list, 15, 6)
        addSubscribers(id, list, 16, 7)
        addSubscribers(id, list, 17, 8)
        addSubscribers(id, list, 18, 9)
        // Run method under test
        val act = out.calculateSubscribersCountByNumberOfInteractions(list)
        // Verify
        act.size.shouldBe(7)
        (act.get(1) as AtomicInteger).get().shouldBe(10)
        (act.get(2) as AtomicInteger).get().shouldBe(11)
        (act.get(3) as AtomicInteger).get().shouldBe(12)
        (act.get(4) as AtomicInteger).get().shouldBe(13)
        (act.get(5) as AtomicInteger).get().shouldBe(14)
        (act.get(6) as AtomicInteger).get().shouldBe(15)
        (act.get(7) as AtomicInteger).get().shouldBe(16+17+18)
    }
    @Test
    fun findItemToIncrement() {
        val out = createSim2Accountant()
        val subscribersCountByNumberOfInteractions =
                hashMapOf(
                        Pair(1, AtomicInteger(10)),
                        Pair(2, AtomicInteger(20)),
                        Pair(3, AtomicInteger(30)),
                        Pair(4, AtomicInteger(40)),
                        Pair(5, AtomicInteger(50)),
                        Pair(6, AtomicInteger(60)),
                        Pair(7, AtomicInteger(70))
                )
        for (i in 1..7) {
            Assertions.assertThat(
                    out.findItemToIncrement(subscribersCountByNumberOfInteractions, 1)
            ).isNotNull
        }
        out.findItemToIncrement(subscribersCountByNumberOfInteractions,
                1)?.get()?.shouldBe(10)
        out.findItemToIncrement(subscribersCountByNumberOfInteractions,
                2)?.get()?.shouldBe(20)
        out.findItemToIncrement(subscribersCountByNumberOfInteractions,
                3)?.get()?.shouldBe(30)
        out.findItemToIncrement(subscribersCountByNumberOfInteractions,
                4)?.get()?.shouldBe(40)
        out.findItemToIncrement(subscribersCountByNumberOfInteractions,
                5)?.get()?.shouldBe(50)
        out.findItemToIncrement(subscribersCountByNumberOfInteractions,
                6)?.get()?.shouldBe(60)
        out.findItemToIncrement(subscribersCountByNumberOfInteractions,
                7)?.get()?.shouldBe(70)
        out.findItemToIncrement(subscribersCountByNumberOfInteractions,
                8)?.get()?.shouldBe(70)
        out.findItemToIncrement(subscribersCountByNumberOfInteractions,
                9)?.get()?.shouldBe(70)
    }
    private fun addSubscribers(id: AtomicInteger,
                               list: ListAgent,
                               numberOfSubscribers: Int,
                               numberOfInteractions: Int) {
        for (i in 1..numberOfSubscribers) {
            list.subscribers.add(
                    Subscriber(id.incrementAndGet().toString(),
                            numberOfInteractions)
            )
        }
    }
}
