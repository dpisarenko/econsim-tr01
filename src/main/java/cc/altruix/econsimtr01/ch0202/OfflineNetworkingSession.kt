package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.DefaultAction
import cc.altruix.econsimtr01.IActionSubscriber
import org.joda.time.DateTime

/**
 * Created by pisarenko on 27.04.2016.
 */
class OfflineNetworkingSession(val offlineNetworkingIntensity:Int,
                               val agent:Protagonist) :
        DefaultAction(
                OfflineNetworkingSessionTriggerFun(
                        offlineNetworkingIntensity,
                        agent
                )
        )  {
    override fun run(time: DateTime) {
        // TODO: Implement this
        // TODO: Test this


        throw UnsupportedOperationException()
    }

    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }
}