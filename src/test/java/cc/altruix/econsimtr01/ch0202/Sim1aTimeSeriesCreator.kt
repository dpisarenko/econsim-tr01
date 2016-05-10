package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.newLine
import org.joda.time.DateTime
import java.io.File

/**
 * Created by pisarenko on 04.05.2016.
 */
open class Sim1aTimeSeriesCreator(val simData: Map<DateTime, Sim1aResultsRow>,
                                  val targetFileName: String,
                                  val simNames: List<String>) {
    fun run() {
        val times = toMutableList()
        sort(times)
        val builder = createStringBuilder()
        builder.append(composeHeader(simData))
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

    open internal fun composeHeader(simData: Map<DateTime, Sim1aResultsRow>): String {
        // TODO: Test this
        val sb = StringBuilder()
        sb.append("t")
        sb.append(";")
        simNames.forEach {
            simName ->
                Sim1aResultRowField.values().forEach {
                    field ->
                        sb.append(simName)
                        sb.append(": ")
                        sb.append(field.description)
                        sb.append(";")
                }
        }
        sb.newLine()
        return sb.toString()
    }

    open internal fun composeRowData(it: DateTime): String {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
