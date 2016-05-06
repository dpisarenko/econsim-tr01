package cc.altruix.econsimtr01.ch0202

import org.joda.time.DateTime
import java.io.File

/**
 * Created by pisarenko on 04.05.2016.
 */
class Sim1aTimeSeriesCreator(val simData: Map<DateTime, Sim1aResultsRow>,
                             val targetFileName: String) {
    fun run() {
        // TODO: Test this
        val times = simData.keys.toMutableList()
        sort(times)
        val builder = createStringBuilder()
        builder.append(composeHeader(simData))
        times.map { composeRowData(it) }.forEach { builder.append(it) }
        writeToFile(builder)
    }

    protected fun writeToFile(builder: StringBuilder) {
        File(targetFileName).writeText(builder.toString())
    }

    private fun createStringBuilder() = StringBuilder()

    private fun sort(times: MutableList<DateTime>) {
        times.sort()
    }

    private fun composeHeader(simData: Map<DateTime, Sim1aResultsRow>): String {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun composeRowData(it: DateTime): String {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
