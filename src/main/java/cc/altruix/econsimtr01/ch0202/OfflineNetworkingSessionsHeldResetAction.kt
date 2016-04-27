package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.DefaultAction
import cc.altruix.econsimtr01.IActionSubscriber
import org.joda.time.DateTime

/**
 * Created by pisarenko on 27.04.2016.
 */
class OfflineNetworkingSessionsHeldResetAction(val agent:Protagonist,
                                               val triggerFun: (DateTime) -> Boolean)
    : DefaultAction(triggerFun){
    override fun run(time: DateTime) {
        agent.offlineNetworkingSessionsHeld = 0
    }

    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }
}