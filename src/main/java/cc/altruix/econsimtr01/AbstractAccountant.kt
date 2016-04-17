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

    fun writeResourceData() {
        resources.forEach { res ->
            val plresname = convertToPrologString(res.name)
            logTarget.append("resource(${res.id}, \"$plresname\", \"${res.unit}\").")
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
