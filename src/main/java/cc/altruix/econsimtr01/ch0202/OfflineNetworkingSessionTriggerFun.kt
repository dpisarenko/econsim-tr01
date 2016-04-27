package cc.altruix.econsimtr01.ch0202

import org.joda.time.DateTime

/**
 * Created by pisarenko on 27.04.2016.
 */
class OfflineNetworkingSessionTriggerFun(val offlineNetworkingIntensity:Int,
                                         val agent:Protagonist) : (DateTime) -> Boolean {
    override fun invoke(p1: DateTime): Boolean {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }
}