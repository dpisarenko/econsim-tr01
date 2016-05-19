/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
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
