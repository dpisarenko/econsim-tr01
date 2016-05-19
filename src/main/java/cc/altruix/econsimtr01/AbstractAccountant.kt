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
 * Created by pisarenko on 14.04.2016.
 */
abstract class AbstractAccountant (val logTarget: StringBuilder,
                          val agents: List<IAgent>,
                          val resources: List<PlResource>) : ISensor{
    val fire: (DateTime) -> Boolean = dailyAtMidnight()
    var firstTime:Boolean = true

    override fun measure(time: DateTime, agents: List<IAgent>) {
        if (fire(time)) {
            if (firstTime) {
                writeResourceData()
                firstTime = false
            }
            logMeasurementTime(time)
            logStockLevels(time.secondsSinceT0())
        }
    }

    fun writeResourceData() {
        resources.forEach { res ->
            val resName = convertToPrologString(res.name)
            logTarget.append("resource(${res.id}, \"$resName\", \"${res.unit}\").")
            logTarget.newLine()
        }
    }

    open fun convertToPrologString(name: String): String
            = name.replace("'", "''")

    open internal fun logStockLevels(time: Long) {
        agents.forEach { agent ->
            resources.forEach { resource ->
                appendResourceAmount(time, agent, resource)
            }
        }
    }

    protected fun appendResourceAmount(time:Long, agent: IAgent, resource: PlResource) {
        if (agent is DefaultAgent) {
            val amt = agent.amount(resource.id)
            logTarget.append("resourceLevel($time, '${agent.id()}', '${resource.id}', $amt).")
            logTarget.newLine()
        }
    }

    protected fun logMeasurementTime(time: DateTime) {
        val dateTimeString = time.toSimulationDateTimeString()
        logTarget.append("measurementTime(${time.secondsSinceT0()}, '$dateTimeString').")
        logTarget.newLine()
    }

}
