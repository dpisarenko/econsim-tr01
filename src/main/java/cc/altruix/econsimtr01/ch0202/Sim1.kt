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
import org.joda.time.DateTime

open class Sim1(val logTarget:StringBuilder,
           val flows:MutableList<ResourceFlow>,
           simParametersProvider: Sim1ParametersProvider) : DefaultSimulation(simParametersProvider) {

    override fun continueCondition(time: DateTime): Boolean = time.year <= 1

    val population = Population(simParametersProvider.initialNetworkSize)
    override fun createAgents(): List<IAgent> {
        val sim1Params = simParametersProvider as Sim1ParametersProvider
        val agents = listOf(
                Protagonist(sim1Params.totalTimeForOfflineNetworkingPerWeek,
                        sim1Params.maxNetworkingSessionsPerBusinessDay,
                        sim1Params.timePerOfflineNetworkingSession,
                        sim1Params.recommendationConversion,
                        sim1Params.willingnessToPurchaseConversion,
                        population
                ),
                Nature()
        )
        return agents
    }

    override fun createSensors(): List<ISensor> {
        return emptyList()
    }
}
