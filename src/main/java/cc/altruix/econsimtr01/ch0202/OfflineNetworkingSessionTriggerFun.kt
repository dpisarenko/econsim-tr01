/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 */

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
        return true
    }
}
