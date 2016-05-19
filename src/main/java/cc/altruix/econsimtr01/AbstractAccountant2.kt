/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 */

package cc.altruix.econsimtr01

import cc.altruix.econsimtr01.ch0202.SimResRow
import org.joda.time.DateTime
import java.util.*

/**
 * Created by pisarenko on 17.05.2016.
 */
abstract class AbstractAccountant2<T>(val resultsStorage: MutableMap<DateTime,
        SimResRow<T>>,
                             val scenarioName: String) : ISensor {
    override fun measure(time: DateTime, agents: List<IAgent>) {
        if (!timeToMeasure(time)) {
            return
        }
        val row: SimResRow<T> = findOrCreateRow(resultsStorage, time)
        val data:MutableMap<T,Double> = findOrCreateDataMap(row, scenarioName)
        saveRowData(agents, data)
    }

    abstract fun timeToMeasure(time: DateTime): Boolean

    abstract fun saveRowData(agents: List<IAgent>,
                         target: MutableMap<T, Double>)

    internal open fun findOrCreateDataMap(row:
                                          SimResRow<T>,
                                          scenarioName: String)
            : MutableMap<T, Double> {
        var dataMap = row.data.get(scenarioName)
        if (dataMap == null) {
            dataMap = HashMap()
            row.data.put(scenarioName, dataMap)
        }
        return dataMap
    }

    internal open fun findOrCreateRow(resultsStorage: MutableMap<DateTime,
            SimResRow<T>>,
                                      time: DateTime)
            : SimResRow<T> {
        var row = resultsStorage.get(time)
        if (row == null) {
            row = SimResRow(time)
            resultsStorage.put(time, row)
        }
        return row
    }
}