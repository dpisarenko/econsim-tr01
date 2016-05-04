package cc.altruix.econsimtr01.ch0202

import org.joda.time.DateTime
import java.util.*

/**
 * Created by pisarenko on 04.05.2016.
 */
class Sim1aResultsRow(val time: DateTime) {
    val data:Map<String,Map<Sim1aResultRowField,Double>> = HashMap()
}