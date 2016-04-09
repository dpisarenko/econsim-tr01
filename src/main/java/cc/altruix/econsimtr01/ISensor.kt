package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 04.04.2016.
 */
interface ISensor {
    fun measure(time: DateTime)
    fun finito()
}