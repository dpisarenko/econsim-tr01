package cc.altruix.econsimtr01

/**
 * Created by pisarenko on 04.04.2016.
 */
class Accountant(val foodStorage: DefaultResourceStorage, val farmer: Farmer) : ISensor {
    val builder = StringBuilder()

    override fun measure(time: Long) {
        builder.append("resourceAvailable($time, 'POTATO', ${foodStorage.amount(Resource.POTATO)}).")
        builder.newLine()
        builder.append("daysWithoutEating($time, ${farmer.daysWithoutFood})")
        builder.newLine()
    }
    override fun finito() {
        System.out.println(builder.toString())
    }
}