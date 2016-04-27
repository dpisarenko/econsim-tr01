package cc.altruix.econsimtr01.ch0202

import org.joda.time.DateTime

/**
 * Created by pisarenko on 27.04.2016.
 */
class OfflineNetworkingSessionTriggerFun(val agent: Protagonist,
                                         val offlineNetworkingIntensity: Int,
                                         val maxNetworkingSessionsPerBusinessDay:Int) : (DateTime) -> Boolean {
    override fun invoke(time: DateTime): Boolean {
        // TODO: Continue here
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }
}