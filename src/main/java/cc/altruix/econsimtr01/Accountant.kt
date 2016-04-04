package cc.altruix.econsimtr01

/**
 * Created by pisarenko on 04.04.2016.
 */
class Accountant(val foodStorage: DefaultResourceStorage, val farmer: Farmer) : ISensor {
    val builder = StringBuilder()
    val fire: (Long) -> Boolean = composeHourMinuteFiringFunction(0, 0)

    override fun measure(time: Long) {
        if ((time % (24 * 60 * 60)) == 0L) {
            builder.append("resourceAvailable($time, 'POTATO', ${foodStorage.amount(Resource.POTATO)}).")
            builder.newLine()
            builder.append("daysWithoutEating($time, ${farmer.daysWithoutFood})")
            builder.newLine()
        }
    }
    override fun finito() {
        System.out.println(builder.toString())
    }
}