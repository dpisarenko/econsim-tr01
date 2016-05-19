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

import cc.altruix.econsimtr01.ch0202.Sim1aResultRowField
import cc.altruix.econsimtr01.ch0202.SimResRow
import cc.altruix.econsimtr01.ch03.RowField
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
open class TimeSeriesCreator<T>(val simData: Map<DateTime,
        SimResRow<T>>,
                           val targetFileName: String,
                           val simNames: List<String>,
                           val fieldsToPrint:Array<T>,
                           val headers:Array<out RowField>) {
    val LOGGER = LoggerFactory.getLogger(TimeSeriesCreator::class.java)
    fun run() {
        val times = toMutableList()
        sort(times)
        val targetFile = File(targetFileName)
        targetFile.appendText(composeHeader())
        times.map {
            composeRowData(it)
        }.forEach {
            targetFile.appendText(it)
        }
    }

    open internal fun toMutableList() = simData.keys.toMutableList()

    open internal fun createStringBuilder() = StringBuilder()

    open internal fun sort(times: MutableList<DateTime>) {
        times.sort()
    }

    open internal fun composeHeader(): String {
        val sb = StringBuilder()
        sb.append("t")
        sb.append(";")
        simNames.forEach {
            simName ->
            headers.forEach {
                field ->
                sb.append("\"")
                sb.append(simName)
                sb.append(": ")
                sb.append(field.description)
                sb.append(" [")
                sb.append(field.unit)
                sb.append("]")
                sb.append("\"")
                sb.append(";")
            }
        }
        sb.append("\n")
        return sb.toString()
    }

    open internal fun composeRowData(time: DateTime): String {
        val data = getTimeData(time)

        if (data == null) {
            LOGGER.error("Can't find data for point in time '${time.toSimulationDateTimeString()}'")
            return "\n"
        }
        val sb = createStringBuilder()
        sb.append(time.toSimulationDateTimeString())
        sb.append(";")
        simNames.forEach {
            simName ->
            val row = data.get(simName)
            if (row == null) {
                appendQuestionMarks(sb)
            } else {
                appendData(row, sb)
            }
        }
        sb.append("\n")
        return sb.toString()
    }

    internal open fun getTimeData(time: DateTime) = simData.get(time)?.data

    protected fun appendData(row: MutableMap<T, Double>, sb: StringBuilder) {
        fieldsToPrint.forEach {
            field ->
            sb.append("\"")
            sb.append(row.get(field))
            sb.append("\"")
            sb.append(";")
        }
    }

    internal fun appendQuestionMarks(sb: StringBuilder) {
        Sim1aResultRowField.values().forEach {
            field ->
            sb.append("\"")
            sb.append("?")
            sb.append("\"")
            sb.append(";")
        }
    }
}
