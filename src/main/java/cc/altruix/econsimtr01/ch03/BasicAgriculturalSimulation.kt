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

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.DefaultSimulation
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ISensor
import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.ch0202.SimResRow
import org.joda.time.DateTime

/**
 * Created by pisarenko on 17.05.2016.
 */
class BasicAgriculturalSimulation(val logTarget:StringBuilder,
                                  val flows:MutableList<ResourceFlow>,
                                  simParametersProvider:
                                  AgriculturalSimParametersProvider,
                                  val resultsStorage:MutableMap<DateTime,
                                          SimResRow<AgriculturalSimulationRowField>>)
: DefaultSimulation(simParametersProvider) {
    override fun continueCondition(time: DateTime): Boolean = time.year <= 3

    override fun createAgents(): List<IAgent> = simParametersProvider.agents

    override fun createSensors(): List<ISensor> =
    listOf(AgriculturalSimulationAccountant(resultsStorage,
            (simParametersProvider as AgriculturalSimParametersProvider)
                    .data["SimulationName"].toString()))
}
