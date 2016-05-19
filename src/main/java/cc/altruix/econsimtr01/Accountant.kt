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

package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 04.04.2016.
 */
class Accountant(val foodStorage: DefaultResourceStorage,
                 val farmer: Farmer,
                 val logTarget: StringBuilder) : ISensor {
    val fire: (DateTime) -> Boolean = dailyAtMidnight()

    override fun measure(time: DateTime, agents: List<IAgent>) {
        if (fire(time)) {
            logMeasurementTime(time)
            logPotatoes(time)
            logDaysWithoutEating(time)
        }
    }

    private fun logDaysWithoutEating(time: DateTime) {
        logTarget.append("daysWithoutEating(${time.secondsSinceT0()}, ${farmer.daysWithoutFood}).")
        logTarget.newLine()
    }

    private fun logPotatoes(time: DateTime) {
        logTarget.append("resourceAvailable(${time.secondsSinceT0()}, 'POTATO', ${foodStorage.amount(Resource.POTATO.name)}).")
        logTarget.newLine()
    }

    private fun logMeasurementTime(time: DateTime) {
        val dateTimeString = time.toSimulationDateTimeString()
        logTarget.append("measurementTime(${time.secondsSinceT0()}, '$dateTimeString').")
        logTarget.newLine()
    }
}
