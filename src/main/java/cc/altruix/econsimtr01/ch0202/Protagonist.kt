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

import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.PlFlow
import cc.altruix.econsimtr01.ch0201.OncePerWeek
import cc.altruix.econsimtr01.dailyAtMidnight
import org.joda.time.DateTime

/**
 * Created by pisarenko on 26.04.2016.
 */
open class Protagonist(val availableTimePerWeek: Int,
                  val maxNetworkingSessionsPerBusinessDay: Int,
                  val timePerOfflineNetworkingSessions:Double,
                  val recommendationConversion:Double,
                  val willingnessToPurchaseConversion:Double,
                  val population: IPopulation) : DefaultAgent(ID) {
    companion object {
        val ID = "protagonist"
    }
    init {
        val mondayMidnight = OncePerWeek("Monday")
        this.addAction(
                PlFlow(
                        id = "prF1",
                        src = "nature",
                        target = ID,
                        resource = Sim1ParametersProvider.RESOURCE_AVAILABLE_TIME.id,
                        amount = availableTimePerWeek.toDouble(),
                        timeTriggerFunction = mondayMidnight
                )
        )
        this.actions.add(
                OfflineNetworkingSessionsHeldDuringDayResetAction(
                        this,
                        dailyAtMidnight()
                )
        )
        this.actions.add(
                OfflineNetworkingSession(
                        this,
                        maxNetworkingSessionsPerBusinessDay,
                        timePerOfflineNetworkingSessions,
                        recommendationConversion,
                        willingnessToPurchaseConversion,
                        population
                )
        )

    }

    var offlineNetworkingSessionsHeldDuringCurrentDay:Int = 0

    override fun act(now: DateTime) {
        super.act(now)
    }
}
