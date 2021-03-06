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

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ISensor
import org.joda.time.DateTime
import java.util.*

/**
 * Created by pisarenko on 04.05.2016.
 */
open class Sim1aAccountant(val resultsStorage: MutableMap<DateTime,
        SimResRow<Sim1aResultRowField>>,
                           val scenarioName: String)
: ISensor {
    override fun measure(time: DateTime, agents: List<IAgent>) {
        val row: SimResRow<Sim1aResultRowField> = findOrCreateRow(resultsStorage, time)
        val data:MutableMap<Sim1aResultRowField,Double> = findOrCreateDataMap(row, scenarioName)
        val protagonist = findProtagonist(agents)
        val population = protagonist.population
        data.put(
                Sim1aResultRowField.PEOPLE_WILLING_TO_MEET,
                calculatePeopleWillingToMeet(time, population)
        )
        data.put(
                Sim1aResultRowField.PEOPLE_WILLING_TO_RECOMMEND,
                calculatePeopleWillingToRecommend(time, population)
        )
        data.put(
                Sim1aResultRowField.PEOPLE_MET,
                calculatePeopleMet(time, population)
        )
        data.put(
                Sim1aResultRowField.PEOPLE_WILLING_TO_PURCHASE,
                calculatePeopleWillingToPurchase(time, population)
        )
    }

    internal open fun findProtagonist(agents: List<IAgent>): Protagonist =
            agents.filter { it is Protagonist }.first() as Protagonist

    internal open fun calculatePeopleWillingToPurchase(time: DateTime,
                                                       population: IPopulation): Double =
            population.people().filter { it.willingToPurchase }.count().toDouble()

    internal open fun calculatePeopleMet(time: DateTime, population: IPopulation): Double =
            population.people().filter { it.offlineNetworkingSessionHeld }.count().toDouble()

    internal open fun calculatePeopleWillingToRecommend(time: DateTime, population: IPopulation): Double =
            population.people().filter { it.willingToRecommend }.count().toDouble()

    internal open fun calculatePeopleWillingToMeet(time: DateTime, population: IPopulation): Double =
            population.people().filter { it.willingToMeet }.count().toDouble()

    internal open fun findOrCreateDataMap(row:
                                          SimResRow<Sim1aResultRowField>,
                                          scenarioName: String)
            : MutableMap<Sim1aResultRowField, Double> {
        var dataMap = row.data.get(scenarioName)
        if (dataMap == null) {
            dataMap = HashMap()
            row.data.put(scenarioName, dataMap)
        }
        return dataMap
    }

    internal open fun findOrCreateRow(resultsStorage: MutableMap<DateTime,
                SimResRow<Sim1aResultRowField>>,
                                      time: DateTime)
            : SimResRow<Sim1aResultRowField> {
        var row = resultsStorage.get(time)
        if (row == null) {
            row = SimResRow(time)
            resultsStorage.put(time, row)
        }
        return row
    }
}