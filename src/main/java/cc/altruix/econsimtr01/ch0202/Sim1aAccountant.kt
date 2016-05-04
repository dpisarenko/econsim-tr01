package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.ISensor
import org.joda.time.DateTime

/**
 * Created by pisarenko on 04.05.2016.
 */
class Sim1aAccountant(val resultsStorage: MutableMap<DateTime, Sim1aResultsRow>,
                      val scenarioName: String)
: ISensor {
    override fun measure(time: DateTime) {
        val row:Sim1aResultsRow = findOrCreateRow(resultsStorage, time)
        val data:Map<Sim1aResultRowField,Double> = findOrCreateDataMap(row, scenarioName)

        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }

    private fun findOrCreateDataMap(row: Sim1aResultsRow, scenarioName: String): Map<Sim1aResultRowField, Double> {
        // TODO: Implement this
        // TODO: Test this

        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    internal open fun findOrCreateRow(resultsStorage: MutableMap<DateTime, Sim1aResultsRow>,
                                      time: DateTime): Sim1aResultsRow {
        // TODO: Test this
        var row = resultsStorage.get(time)
        if (row == null) {
            row = Sim1aResultsRow(time)
            resultsStorage.put(time, row)
        }
        return row
    }
}