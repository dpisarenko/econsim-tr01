package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime

/**
 * Created by pisarenko on 11.04.2016.
 */
class Sim1Accountant(val logTarget: StringBuilder,
                     val agents: List<IAgent>) : ISensor {
    val fire: (DateTime) -> Boolean = dailyAtMidnight()

    override fun measure(time: DateTime) {
        if (fire(time)) {
            logMeasurementTime(time)
        }
    }
    private fun logMeasurementTime(time: DateTime) {
        val dateTimeString = time.toSimulationDateTimeString()
        logTarget.append("measurementTime(${time.secondsSinceT0()}, '$dateTimeString').")
        logTarget.newLine()
    }
}