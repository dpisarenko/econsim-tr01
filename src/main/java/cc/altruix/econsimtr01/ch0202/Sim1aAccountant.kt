package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.ISensor
import org.joda.time.DateTime

/**
 * Created by pisarenko on 04.05.2016.
 */
class Sim1aAccountant(val resultsStorage:Map<DateTime,Sim1aResultsRow>)
: ISensor {
    override fun measure(time: DateTime) {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }
}