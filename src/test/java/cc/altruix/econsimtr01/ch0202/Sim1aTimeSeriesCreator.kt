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

package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.toSimulationDateTimeString
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import java.io.File

/**
 * Created by pisarenko on 04.05.2016.
 */
open class Sim1aTimeSeriesCreator(val simData: Map<DateTime, SimResRow<Sim1aResultRowField>>,
                                  val targetFileName: String,
                                  val simNames: List<String>) {
    val LOGGER = LoggerFactory.getLogger(Sim1aTimeSeriesCreator::class.java)
    fun run() {
        val times = toMutableList()
        sort(times)
        val builder = createStringBuilder()
        builder.append(composeHeader())
        times.map {
            composeRowData(it)
        }.forEach {
            builder.append(it)
        }
        writeToFile(builder)
    }

    open internal fun toMutableList() = simData.keys.toMutableList()

    open internal fun writeToFile(builder: StringBuilder) {
        File(targetFileName).writeText(builder.toString())
    }

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
                Sim1aResultRowField.values().forEach {
                    field ->
                        sb.append("\"")
                        sb.append(simName)
                        sb.append(": ")
                        sb.append(field.description)
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

    protected fun appendData(row: MutableMap<Sim1aResultRowField, Double>, sb: StringBuilder) {
        Sim1aResultRowField.values().forEach {
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
