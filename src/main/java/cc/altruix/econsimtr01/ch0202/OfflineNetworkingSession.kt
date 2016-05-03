package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.DefaultAction
import cc.altruix.econsimtr01.IActionSubscriber
import org.joda.time.DateTime

/**
 * Created by pisarenko on 27.04.2016.
 */
class OfflineNetworkingSession(val agent: Protagonist,
                               val maxNetworkingSessionsPerBusinessDay: Int,
                               val timePerOfflineNetworkingSession:Double,
                               population: IPopulation) :
        DefaultAction(
                OfflineNetworkingSessionTriggerFun(
                        agent,
                        maxNetworkingSessionsPerBusinessDay
                )
        )  {
    override fun run(time: DateTime) {
        // Check, if we have time for another networking session

        if (!validate()) {
            return
        }

        // TODO: Continue here
        // TODO: Implement this
        // TODO: Test this



        throw UnsupportedOperationException()
    }

    fun validate():Boolean {
        // TODO: Test this
        if (agent.offlineNetworkingSessionsHeldDuringCurrentDay >= maxNetworkingSessionsPerBusinessDay) {
            return false
        }

        // TODO: Test this
        if (agent.amount(Sim1.RESOURCE_AVAILABLE_TIME.id) < timePerOfflineNetworkingSession) {
            return false
        }

        // TODO: Test this
        return true
    }

    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }
}
