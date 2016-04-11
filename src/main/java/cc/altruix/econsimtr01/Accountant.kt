package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 04.04.2016.
 */
class Accountant(val foodStorage: DefaultResourceStorage,
                 val farmer: Farmer,
                 val logTarget: StringBuilder) : ISensor {
    val fire: (DateTime) -> Boolean = dailyAtMidnight()

    override fun measure(time: DateTime) {
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


    override fun finito() {
    }
}
