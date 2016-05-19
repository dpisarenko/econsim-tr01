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

import cc.altruix.econsimtr01.DefaultAction
import cc.altruix.econsimtr01.IActionSubscriber
import org.joda.time.DateTime

/**
 * Created by pisarenko on 28.04.2016.
 */
class OfflineNetworkingSessionsHeldDuringDayResetAction(val agent:Protagonist,
                                                        val triggerFun: (DateTime) -> Boolean)
    : DefaultAction(triggerFun){
    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }

    override fun run(time: DateTime) {
        agent.offlineNetworkingSessionsHeldDuringCurrentDay = 0
    }
}