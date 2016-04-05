package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 04.04.2016.
 */
class Accountant(val foodStorage: DefaultResourceStorage,
                 val farmer: Farmer,
                 val logTarget: StringBuilder) : ISensor {
    val fire: (Long) -> Boolean = composeHourMinuteFiringFunction(0, 0)

    override fun measure(time: Long) {
        if ((time % (24 * 60 * 60)) == 0L) {



            val period = time.toPeriod()

            val t0 = DateTime(0, 0, 0, 0, 0, 0, 0)
            val t = t0.plus(period)

            val hours = t.hourOfDay.toFixedLengthString(2)
            val minutes = period.minutes.toFixedLengthString(2)
            val year = period.years.toFixedLengthString(4)
            val months = period.months.toFixedLengthString(2)
            val days = period.days.toFixedLengthString(2)
            logTarget.append("measurementTime($time, '$year-$months-$days $hours:$minutes').")
            logTarget.append("resourceAvailable($time, 'POTATO', ${foodStorage.amount(Resource.POTATO)}).")
            logTarget.newLine()
            logTarget.append("daysWithoutEating($time, ${farmer.daysWithoutFood})")
            logTarget.newLine()
        }
    }
    override fun finito() {
    }
}
