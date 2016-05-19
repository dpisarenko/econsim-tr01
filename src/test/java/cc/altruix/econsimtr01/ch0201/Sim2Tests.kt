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

import cc.altruix.econsimtr01.*
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 13.04.2016.
 */
class Sim2Tests {
    @Test
    fun test() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim2ParametersProvider(
                File("src/test/resources/ch0201Sim2Tests.params.pl").readText()
        )
        val sim = Sim2(
                log,
                flows,
                simParametersProvider
        )
        simulationRunLogic(sim,
                log,
                simParametersProvider.resources,
                flows,
                "src/test/resources/ch0201/sim02/Sim2Tests.test.pl.expected.txt",
                "src/test/resources/ch0201/sim02/Sim2Tests.test.csv.expected.txt",
                "src/test/resources/ch0201/sim02/Sim2Tests.test.flows.actual.png",
                Sim2TimeSeriesCreator()
        )
    }
    @Test
    fun addSubscribers() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim2ParametersProvider(
                File("src/test/resources/ch0201Sim2Tests.params.pl").readText()
        )
        val sim = Sim2(
                log,
                flows,
                simParametersProvider
        )
        val list = sim.findAgent("list") as ListAgent
        verifySubscriberCreation(list, sim, "r06-pc1", 1)
        verifySubscriberCreation(list, sim, "r07-pc2", 2)
        verifySubscriberCreation(list, sim, "r08-pc3", 3)
        verifySubscriberCreation(list, sim, "r09-pc4", 4)
        verifySubscriberCreation(list, sim, "r10-pc5", 5)
        verifySubscriberCreation(list, sim, "r11-pc6", 6)
        verifySubscriberCreation(list, sim, "r12-pc7", 7)
    }

    @Test
    fun setInitialResourceLevel() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim2ParametersProvider(
                File("src/test/resources/ch0201Sim2Tests.params.pl").readText()
        )
        val sim = Sim2(
                log,
                flows,
                simParametersProvider
        )
        val agent = DefaultAgent("id1")
        val irl = InitialResourceLevel("id1", "r2", 12.34)
        agent.storage.amount("r2").shouldBe(0.0)
        // Run method under test
        sim.setInitialResourceLevel2(agent, irl)
        // Verify
        agent.storage.amount("r2").shouldBe(12.34)
    }

    @Test
    fun setInitialResourceLevelSetsInitialResourceLevelForNormalResources() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim2ParametersProvider(
                File("src/test/resources/ch0201Sim2Tests.params.pl").readText()
        )
        val sim = Mockito.spy(
                Sim2(
                        log,
                        flows,
                        simParametersProvider
                )
        )
        val agent = DefaultAgent("id1")
        val irl = InitialResourceLevel("id1", "r2", 123.45)
        // Run method under test
        sim.setInitialResourceLevel(agent, irl)
        // Verify
        Mockito.verify(sim).setInitialResourceLevel2(agent, irl)
        agent.storage.amount("r2").shouldBe(123.45)
    }

    @Test
    fun continueConditionReturnsFalseWhenItsTooLate() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim2ParametersProvider(
                File("src/test/resources/ch0201Sim2Tests.params.pl").readText()
        )
        val sim = Sim2(
                log,
                flows,
                simParametersProvider
        )
        // Run method under test
        sim.continueCondition(31536000L.secondsToSimulationDateTime()).shouldBeTrue()
        sim.continueCondition(31622400L.secondsToSimulationDateTime()).shouldBeFalse()
        sim.continueCondition(31708800L.secondsToSimulationDateTime()).shouldBeFalse()
    }

    @Test
    fun setInitialResourceLevelCallsAddSubscribers() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim2ParametersProvider(
                File("src/test/resources/ch0201Sim2Tests.params.pl").readText()
        )
        val sim = Mockito.spy(
                Sim2(
                        log,
                        flows,
                        simParametersProvider
                )
        )
        val list = ListAgent("list")
        val irl = InitialResourceLevel("list", "r06-pc1", 1000.0)
        Mockito.doNothing().`when`(sim).addSubscribers(list, irl)
        // Run method under test
        sim.setInitialResourceLevel(list, irl)
        // Verify
        Mockito.verify(sim).addSubscribers(list, irl)
    }

    private fun verifySubscriberCreation(list: ListAgent,
                                         sim: Sim2,
                                         resId: String,
                                         interactions: Int) {
        list.subscribers.clear()
        val irl = InitialResourceLevel("list", resId, 10.0)
        sim.addSubscribers(list, irl)
        list.subscribers.size.shouldBe(10)
        list.subscribers.forEach {
            Assertions.assertThat(it.interactionsWithStacy).isEqualTo(interactions)
        }
    }
}
