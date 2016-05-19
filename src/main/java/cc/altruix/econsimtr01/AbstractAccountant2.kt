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