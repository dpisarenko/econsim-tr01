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
