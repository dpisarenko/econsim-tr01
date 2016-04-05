package cc.altruix.econsimtr01

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
            val hours = period.hours
            val minutes = period.minutes
            val year = String.format("%04d", period.years)
            val months = period.months
            val days = period.days
            logTarget.append("measurementTime($time, '$year-').")
            logTarget.append("resourceAvailable($time, 'POTATO', ${foodStorage.amount(Resource.POTATO)}).")
            logTarget.newLine()
            logTarget.append("daysWithoutEating($time, ${farmer.daysWithoutFood})")
            logTarget.newLine()
        }
    }
    override fun finito() {
    }
}
