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

package cc.altruix.econsimtr01

import cc.altruix.econsimtr01.ch0201.InfiniteResourceSupply
import cc.altruix.econsimtr01.ch0201.InitialResourceLevel
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * Created by pisarenko on 18.04.2016.
 */
class DefaultSimulationTests {
    private class SimParametersProvider(override val agents: MutableList<IAgent>,
                                override val flows: MutableList<PlFlow>,
                                override val initialResourceLevels: MutableList<InitialResourceLevel>,
                                override val infiniteResourceSupplies: MutableList<InfiniteResourceSupply>) :
            ISimParametersProvider {
        override val transformations:MutableList<PlTransformation> = LinkedList()
            get

    }
    private open class DefaultSimulationForTesting : DefaultSimulation(
            SimParametersProvider(
                    LinkedList<IAgent>(),
                    LinkedList<PlFlow>(),
                    LinkedList<InitialResourceLevel>(),
                    LinkedList<InfiniteResourceSupply>()
            )
    ) {
        override fun continueCondition(tick: DateTime): Boolean = true
        override fun createAgents(): List<IAgent> = emptyList()
        override fun createSensors(): List<ISensor> = emptyList()
    }

    private open class DefaultSimulationForTesting2 : DefaultSimulation(
            SimParametersProvider(
                    LinkedList<IAgent>(),
                    LinkedList<PlFlow>(),
                    LinkedList<InitialResourceLevel>(),
                    LinkedList<InfiniteResourceSupply>()
            )
    ) {
        var fired = false
        override fun continueCondition(tick: DateTime): Boolean {
            if (!fired) {
                fired = true
                return true
            }
            return false
        }
        override fun createAgents(): List<IAgent> = emptyList()
        override fun createSensors(): List<ISensor> = emptyList()
    }

    @Test
    fun runTicksEveryMinute() {
        val out = DefaultSimulationForTesting()
        val t0 = 0L.millisToSimulationDateTime()
        val act1 = out.minimalSimulationCycle(emptyList(), emptyList(), t0, emptyList<IAction>())
        val t1 = t0.plusMinutes(1)
        Assertions.assertThat(act1).isEqualTo(t1)

        val act2 = out.minimalSimulationCycle(emptyList(), emptyList(), t1, emptyList<IAction>())
        val t2 = t1.plusMinutes(1)
        Assertions.assertThat(act2).isEqualTo(t2)
    }
    @Test
    fun runWiring() {
        // Prepare
        val out = Mockito.spy(DefaultSimulationForTesting2())
        val agents = mock<List<IAgent>>()
        val sensors = mock<List<ISensor>>()
        val unattachedProcesses = mock<List<IAction>>()
        Mockito.doReturn(agents).`when`(out).createAgents()
        Mockito.doReturn(sensors).`when`(out).createSensors()
        Mockito.doReturn(unattachedProcesses).`when`(out).createUnattachedProcesses()
        val t = 0L.millisToSimulationDateTime()
        Mockito.doReturn(t.plusMinutes(1)).`when`(out).minimalSimulationCycle(
                agents,
                sensors,
                t,
                unattachedProcesses
        )
        // Run method under test
        out.run()
        // Verify
        Mockito.verify(out).createAgents()
        Mockito.verify(out).createSensors()
        Mockito.verify(out).createUnattachedProcesses()
        Mockito.verify(out).minimalSimulationCycle(
                agents,
                sensors,
                t,
                unattachedProcesses
        )
    }
    @Test
    fun minimalSimulationCycleWiring() {
        // Prepare
        val out = Mockito.spy(DefaultSimulationForTesting2())
        val agent = mock<IAgent>()
        val agents = listOf(agent)
        val sensor = mock<ISensor>()
        val sensors = listOf(sensor)
        val unattachedProcess = mock<IAction>()
        val unattachedProcesses = listOf(unattachedProcess)
        val t = 0L.millisToSimulationDateTime()
        val newTime = t.plusMinutes(1)
        // Run method under test
        val actResult = out.minimalSimulationCycle(
                agents,
                sensors,
                t,
                unattachedProcesses
        )
        // Verify
        Assertions.assertThat(actResult).isEqualTo(newTime)
        Mockito.verify(agent).act(newTime)
        Mockito.verify(unattachedProcess).run(newTime)
        Mockito.verify(sensor).measure(newTime, agents)
    }
}
