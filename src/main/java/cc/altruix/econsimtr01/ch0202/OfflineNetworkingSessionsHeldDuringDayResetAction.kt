/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
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