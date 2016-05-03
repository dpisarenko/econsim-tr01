package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.isBusinessDay
import org.joda.time.DateTime

/**
 * Created by pisarenko on 27.04.2016.
 */
class OfflineNetworkingSessionTriggerFun(val agent: Protagonist,
                                         val maxNetworkingSessionsPerBusinessDay:Int)
    : (DateTime) -> Boolean {
    override fun invoke(time: DateTime): Boolean {
        /**
         * The protagonist works on business days only.
         */
        if (!time.isBusinessDay()) {
            return false;
        }
        /**
         * Did we exceed our limit of networking sessions per day?
         */
        if (agent.offlineNetworkingSessionsHeldDuringCurrentDay >= maxNetworkingSessionsPerBusinessDay) {
            return false
        }

        /**
         * Question: Do we care about time?
         * Answer: No
         */
        // TODO: Test this
        return true
    }
}
