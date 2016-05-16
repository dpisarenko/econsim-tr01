/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ISensor
import org.joda.time.DateTime
import java.util.*

/**
 * Created by pisarenko on 04.05.2016.
 */
open class Sim1aAccountant(val resultsStorage: MutableMap<DateTime, Sim1aResultsRow>,
                      val scenarioName: String)
: ISensor {
    override fun measure(time: DateTime, agents: List<IAgent>) {
        val row:Sim1aResultsRow = findOrCreateRow(resultsStorage, time)
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

    internal open fun findOrCreateDataMap(row: Sim1aResultsRow,
                                    scenarioName: String)
            : MutableMap<Sim1aResultRowField, Double> {
        var dataMap = row.data.get(scenarioName)
        if (dataMap == null) {
            dataMap = HashMap()
            row.data.put(scenarioName, dataMap)
        }
        return dataMap
    }

    internal open fun findOrCreateRow(resultsStorage: MutableMap<DateTime, Sim1aResultsRow>,
                                      time: DateTime)
            : Sim1aResultsRow {
        var row = resultsStorage.get(time)
        if (row == null) {
            row = Sim1aResultsRow(time)
            resultsStorage.put(time, row)
        }
        return row
    }
}