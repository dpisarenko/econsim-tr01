package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime

/**
 * Created by pisarenko on 11.04.2016.
 */
class Sim1Accountant(val logTarget: StringBuilder,
                     val agents: List<IAgent>,
                     val resources: List<PlResource>) : ISensor {
    val fire: (DateTime) -> Boolean = dailyAtMidnight()
    var firstTime:Boolean = true

    override fun measure(time: DateTime) {
        if (fire(time)) {
            if (firstTime) {
                writeResourceData()
                firstTime = false
            }
            logMeasurementTime(time)
            logStockLevels(time.secondsSinceT0())
        }
    }

    private fun writeResourceData() {
        resources.forEach { res ->
            logTarget.append("resource(${res.id}, \"${res.name}\", \"${res.unit}\").")
            logTarget.newLine()
        }
    }

    private fun logStockLevels(time: Long) {
        agents.forEach { agent ->
            resources.forEach { resource ->
                appendResourceAmount(time, agent, resource)
            }
        }
    }

    private fun appendResourceAmount(time:Long, agent: IAgent, resource: PlResource) {
        if (agent is DefaultAgent) {
            val amt = agent.amount(resource.id)
            logTarget.append("resourceLevel($time, '${agent.id()}', '${resource.id}', $amt).")
            logTarget.newLine()
        }
    }

    private fun logMeasurementTime(time: DateTime) {
        val dateTimeString = time.toSimulationDateTimeString()
        logTarget.append("measurementTime(${time.secondsSinceT0()}, '$dateTimeString').")
        logTarget.newLine()
    }
}
