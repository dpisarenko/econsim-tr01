package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 15.04.2016.
 */
interface IActionSubscriber {
    fun actionOccurred(time: DateTime)
}