package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.ISensor
import org.joda.time.DateTime
import java.util.*

/**
 * Created by pisarenko on 04.05.2016.
 */
open class Sim1aAccountant(val resultsStorage: MutableMap<DateTime, Sim1aResultsRow>,
                      val scenarioName: String)
: ISensor {
    override fun measure(time: DateTime) {
        val row:Sim1aResultsRow = findOrCreateRow(resultsStorage, time)
        val data:MutableMap<Sim1aResultRowField,Double> = findOrCreateDataMap(row, scenarioName)
        data.put(Sim1aResultRowField.PEOPLE_WILLING_TO_MEET, calculatePeopleWillingToMeet(time))
        data.put(Sim1aResultRowField.PEOPLE_WILLING_TO_RECOMMEND, calculatePeopleWillingToRecommend(time))
        data.put(Sim1aResultRowField.PEOPLE_MET, calculatePeopleMet(time))
        data.put(Sim1aResultRowField.PEOPLE_WILLING_TO_PURCHASE, calculatePeopleWillingToPurchase(time))
    }

    internal open fun calculatePeopleWillingToPurchase(time: DateTime): Double {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    internal open fun calculatePeopleMet(time: DateTime): Double {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    internal open fun calculatePeopleWillingToRecommend(time: DateTime): Double {
        // TODO: Implement this
        // TODO: Test this

        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    internal open fun calculatePeopleWillingToMeet(time: DateTime): Double {
        // TODO: Implement this
        // TODO: Test this

        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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