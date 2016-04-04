package cc.altruix.econsimtr01

/**
 * Created by pisarenko on 04.04.2016.
 */
interface ISensor {
    fun measure(time:Long)
    fun finito()
}