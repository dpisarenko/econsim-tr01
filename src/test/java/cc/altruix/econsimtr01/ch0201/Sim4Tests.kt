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
import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 19.04.2016.
 */
class Sim4Tests {
    @Test
    fun test() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        val sim = Sim4(
                log,
                flows,
                simParametersProvider
        )
        simulationRunLogic(sim,
                log,
                simParametersProvider.resources,
                flows,
                "src/test/resources/ch0201/sim04/Sim4Tests.test.pl.expected.txt",
                "src/test/resources/ch0201/sim04/Sim4Tests.test.csv.expected.txt",
                "src/test/resources/ch0201/sim04/Sim4Tests.test.flows.actual.png",
                Sim4TimeSeriesCreator()
        )
    }
    @Test
    fun continueCondition() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        val out = Sim4(
                log,
                flows,
                simParametersProvider
        )
        out.continueCondition(DateTime(0, 1, 1, 0, 0, 0)).shouldBeTrue()
        out.continueCondition(DateTime(1, 1, 1, 0, 0, 0)).shouldBeTrue()
        out.continueCondition(DateTime(2, 1, 1, 0, 0, 0)).shouldBeFalse()
    }
    @Test
    fun createAgentsCallsAttachTransformationsToAgents() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        val out = Mockito.spy(
                Sim4(
                        log,
                        flows,
                        simParametersProvider
                )
        )
        Mockito.doNothing().`when`(out).attachFlowsToAgents(simParametersProvider.flows,
                simParametersProvider.agents,
                flows)
        Mockito.doNothing().`when`(out).attachTransformationsToAgents(
                simParametersProvider.transformations,
                simParametersProvider.agents
        )
        Mockito.doNothing().`when`(out).setInitialResourceLevels()
        Mockito.doNothing().`when`(out).setInfiniteResourceSupplies()
        // Run method under test
        out.createAgents()
        // Verify
        Mockito.verify(out).attachFlowsToAgents(simParametersProvider.flows,
                simParametersProvider.agents,
                flows)
        Mockito.verify(out).attachTransformationsToAgents(
                simParametersProvider.transformations,
                simParametersProvider.agents
        )
        Mockito.verify(out).setInitialResourceLevels()
        Mockito.verify(out).setInfiniteResourceSupplies()
    }
    @Test
    fun attachTransformationsToAgentsCallsAttachTransformationToAgentForAllTrs() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        val out = Mockito.spy(
                Sim4(
                        log,
                        flows,
                        simParametersProvider
                )
        )
        val agents = emptyList<IAgent>()
        val tr1 = mock<PlTransformation>()
        val tr2 = mock<PlTransformation>()
        val trs = mutableListOf(tr1, tr2)
        Mockito.doNothing().`when`(out).attachTransformationToAgent(agents, tr1)
        Mockito.doNothing().`when`(out).attachTransformationToAgent(agents, tr2)
        // Run method under test
        out.attachTransformationsToAgents(trs, agents)
        // Verify
        Mockito.verify(out).attachTransformationToAgent(agents, tr1)
        Mockito.verify(out).attachTransformationToAgent(agents, tr2)
    }
    @Test
    fun attachTransformationToAgentCallsAddTransformation() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        val out = Mockito.spy(
                Sim4(
                        log,
                        flows,
                        simParametersProvider
                )
        )
        val agent = mock<DefaultAgent>()
        val agents = emptyList<IAgent>()
        Mockito.doReturn(agent).`when`(out).findAgent(agents, "agent")
        val tr = PlTransformation("id",
                "agent",
                1.0,
                "res1",
                2.0,
                "res2",
                {false})
        // Run method under test
        out.attachTransformationToAgent(agents, tr)
        // Verify
        Assertions.assertThat(tr.agents).isSameAs(agents)
        Mockito.verify(agent).addTransformation(tr)
    }
    @Test
    fun createSensorsAddsSim4Accountant() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        val out = Mockito.spy(
                Sim4(
                        log,
                        flows,
                        simParametersProvider
                )
        )
        val sensors = out.createSensors()
        Assertions.assertThat(sensors).isNotNull
        sensors.size.shouldBe(1)
        Assertions.assertThat(sensors.get(0) is Sim4Accountant).isTrue()
    }
    @Test
    fun agentsPropertyOfPlTransformationsIsInitialized() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        val out = Sim4(
                log,
                flows,
                simParametersProvider
        )
        // Run method under test
        out.createAgents()
        // Verify
        simParametersProvider.transformations.size.shouldBe(2)
        simParametersProvider.transformations.forEach {
            Assertions.assertThat(it.agents).isNotNull
        }
    }
}