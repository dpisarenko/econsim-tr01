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
import cc.altruix.econsimtr01.randomEventWithProbability
import org.joda.time.DateTime

/**
 * Created by pisarenko on 27.04.2016.
 */
open class OfflineNetworkingSession(val agent: Protagonist,
                               val maxNetworkingSessionsPerBusinessDay: Int,
                               val timePerOfflineNetworkingSession:Double,
                               val recommendationConversion:Double,
                               val willingnessToPurchaseConversion:Double,
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
            agent.remove(Sim1ParametersProvider.RESOURCE_AVAILABLE_TIME.id, timePerOfflineNetworkingSession)
            updateWillingnessToRecommend(meetingPartner)
            updateWillingnessToPurchase(meetingPartner)
            meetingPartner.offlineNetworkingSessionHeld = true
        }
    }

    open fun updateWillingnessToPurchase(meetingPartner: Person) {
        if (experiment(willingnessToPurchaseConversion)) {
            meetingPartner.willingToPurchase = true
        }
    }

    open fun updateWillingnessToRecommend(meetingPartner: Person) {
        if (experiment(recommendationConversion)) {
            meetingPartner.willingToRecommend = true
        }
    }

    open fun experiment(probability:Double):Boolean = randomEventWithProbability(probability)

    open fun findMeetingPartner(): Person? = population.people()
                .filter { it.willingToMeet && !it.offlineNetworkingSessionHeld }
                .firstOrNull()

    open fun validate():Boolean {
        if (agent.offlineNetworkingSessionsHeldDuringCurrentDay >= maxNetworkingSessionsPerBusinessDay) {
            return false
        }
        if (agent.amount(Sim1ParametersProvider.RESOURCE_AVAILABLE_TIME.id) < timePerOfflineNetworkingSession) {
            return false
        }
        return true
    }

    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }
}
