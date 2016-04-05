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
        val period = time.toPeriod()
        val t0 = DateTime(0, 1, 1, 0, 0, 0, 0)
        val t = t0.plus(period)
        val hours = t.hourOfDay.toFixedLengthString(2)
        val minutes = t.minuteOfHour.toFixedLengthString(2)
        val year = t.year.toFixedLengthString(4)
        val months = t.monthOfYear.toFixedLengthString(2)
        val days = t.dayOfMonth.toFixedLengthString(2)
        logTarget.append("measurementTime($time, '$year-$months-$days $hours:$minutes').")
        logTarget.newLine()
    }

    override fun finito() {
    }
}
