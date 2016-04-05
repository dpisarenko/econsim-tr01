package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 04.04.2016.
 */
class Accountant(val foodStorage: DefaultResourceStorage,
                 val farmer: Farmer,
                 val logTarget: StringBuilder) : ISensor {
    val fire: (Long) -> Boolean = dailyAtMidnight()

    override fun measure(time: Long) {
        if (fire(time)) {
            logMeasurementTime(time)
            logPotatoes(time)
            logDaysWithoutEating(time)
        }
    }

    private fun logDaysWithoutEating(time: Long) {
        logTarget.append("daysWithoutEating($time, ${farmer.daysWithoutFood}).")
        logTarget.newLine()
    }

    private fun logPotatoes(time: Long) {
        logTarget.append("resourceAvailable($time, 'POTATO', ${foodStorage.amount(Resource.POTATO)}).")
        logTarget.newLine()
    }

    private fun logMeasurementTime(time: Long) {
        val dateTimeString = time.toSimulationDateTimeString()
        logTarget.append("measurementTime($time, '$dateTimeString').")
        logTarget.newLine()
    }


    override fun finito() {
    }
}
