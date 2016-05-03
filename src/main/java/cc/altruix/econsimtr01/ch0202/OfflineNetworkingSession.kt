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
                               val population: IPopulation) :
        DefaultAction(
                OfflineNetworkingSessionTriggerFun(
                        agent,
                        maxNetworkingSessionsPerBusinessDay
                )
        )  {
    override fun run(time: DateTime) {
        if (!validate()) {
            return
        }
        val meetingPartner = findMeetingPartner()
        if (meetingPartner != null) {
            agent.offlineNetworkingSessionsHeldDuringCurrentDay++
            agent.remove(Sim1.RESOURCE_AVAILABLE_TIME.id, timePerOfflineNetworkingSession)
            updateWillingnessToRecommend(meetingPartner)
            updateWillingnessToPurchase(meetingPartner)
        }
        // TODO: Continue here
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }

    open protected fun updateWillingnessToPurchase(meetingPartner: Person) {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    open protected fun updateWillingnessToRecommend(meetingPartner: Person) {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // TODO: Test this
    protected fun findMeetingPartner(): Person? = population.people()
                .filter { it.willingToMeet && !it.offlineNetworkingSessionHeld }
                .firstOrNull()

    fun validate():Boolean {
        if (agent.offlineNetworkingSessionsHeldDuringCurrentDay >= maxNetworkingSessionsPerBusinessDay) {
            return false
        }
        if (agent.amount(Sim1.RESOURCE_AVAILABLE_TIME.id) < timePerOfflineNetworkingSession) {
            return false
        }
        return true
    }

    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }
}
