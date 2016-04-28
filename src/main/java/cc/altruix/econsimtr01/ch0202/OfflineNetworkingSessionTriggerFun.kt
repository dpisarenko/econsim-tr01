package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.isBusinessDay
import org.joda.time.DateTime

/**
 * Created by pisarenko on 27.04.2016.
 */
class OfflineNetworkingSessionTriggerFun(val agent: Protagonist,
                                         val offlineNetworkingIntensity: Int,
                                         val maxNetworkingSessionsPerBusinessDay:Int) : (DateTime) -> Boolean {
    override fun invoke(time: DateTime): Boolean {
        if (!time.isBusinessDay()) {
            return false;
        }

        // if (agent.offlineNetworkingSessionsHeldDuringCurrentWeek)

        // TODO: Continue here
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }
}