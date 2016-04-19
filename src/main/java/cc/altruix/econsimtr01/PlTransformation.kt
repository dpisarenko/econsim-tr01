package cc.altruix.econsimtr01

import org.joda.time.DateTime
import java.util.*

/**
 * Created by pisarenko on 19.04.2016.
 */
class PlTransformation(val id:String,
                       val agent:String,
                       val inputAmount:Double,
                       val inputResourceId:String,
                       val outputAmuont:Double,
                       val outputResourceId:String,
                       val timeTriggerFunction: (DateTime) -> Boolean) : IAction {
    val subscribers : MutableList<IActionSubscriber> = LinkedList<IActionSubscriber>()
    // TODO: Test this
    override fun timeToRun(time: DateTime): Boolean = timeTriggerFunction(time)

    override fun run(time: DateTime) {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }

    override fun notifySubscribers(time: DateTime) {
        // TODO: Test this
        this.subscribers.forEach { it.actionOccurred(this, time) }
    }

    override fun subscribe(subscriber: IActionSubscriber) {
        // TODO: Test this
        this.subscribers.add(subscriber)
    }
}