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

package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.*
import cc.altruix.econsimtr01.ch0201.OncePerWeek
import org.joda.time.DateTime
import java.util.*

/**
 * Created by pisarenko on 04.05.2016.
 */
class Sim1a(logTarget:StringBuilder,
            flows:MutableList<ResourceFlow>,
            simParametersProvider: Sim1ParametersProvider,
            val resultsStorage:MutableMap<DateTime, SimResRow<Sim1aResultRowField>>) :
        Sim1(logTarget, flows, simParametersProvider) {
    override fun createAgents(): List<IAgent> {
        val agents = super.createAgents()
        val plFlows = LinkedList<PlFlow>()
        agents.forEach { agt ->
            if (agt is DefaultAgent) {
                agt.actions
                        .filter { it is PlFlow }
                        .forEach { plFlows.add(it as PlFlow) }
            }
        }
        plFlows.forEach { flow ->
            flow.agents = agents
            flow.flows = flows
        }
        return agents
    }

    override fun createUnattachedProcesses(): List<IAction> {
        val sim1Params = simParametersProvider as Sim1ParametersProvider
        return listOf(
                IntroductionProcess(
                population = population,
                triggerFun = OncePerWeek("Monday"),
                averageNetworkActivity =
                    sim1Params.averageNetworkActivity,
                averageSuggestibilityOfStrangers =
                    sim1Params.averageSuggestibilityOfStrangers
                )
        )
    }
    override fun createSensors(): List<ISensor> {
        return listOf(
                Sim1aAccountant(
                        resultsStorage,
                        (simParametersProvider as Sim1ParametersProvider).name
                )
        )
    }
}
