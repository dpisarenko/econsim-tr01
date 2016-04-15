package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.IAction
import cc.altruix.econsimtr01.IActionSubscriber
import cc.altruix.econsimtr01.PlFlow
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import java.util.*

/**
 * Created by pisarenko on 14.04.2016.
 */
open class ListAgent(id:String) : DefaultAgent(id), IActionSubscriber {
    val LOGGER = LoggerFactory.getLogger(ListAgent::class.java)
    companion object {
        val subscriberTypes = hashMapOf(
                Pair("r06-pc1", 1),
                Pair("r07-pc2", 2),
                Pair("r08-pc2", 3),
                Pair("r09-pc2", 4),
                Pair("r10-pc2", 5),
                Pair("r11-pc2", 6),
                Pair("r12-pc2", 7)
        )
    }
    val random = Random()
    val subscribers : MutableList<Subscriber> = LinkedList<Subscriber>()
    var buyersCount : Int = 0
    override fun put(res: String, amt: Double) {
        if (subscriberTypes.containsKey(res)) {
            addSubscribers(res, amt)
        } else {
            super.put(res, amt)
        }
    }

    open fun addSubscribers(res: String, amt: Double) {
        if (!subscriberTypes.containsKey(res)) {
            LOGGER.error("Unknown subscriber type '$res'")
            return
        }
        val number = Math.ceil(amt).toInt()
        val interactions = subscriberTypes.get(res) as Int
        for (i in 1..number) {
            subscribers.add(Subscriber(UUID.randomUUID().toString(), interactions))
        }
    }

    open override fun addAction(action: PlFlow) {
        super.addAction(action)
        if (action.id == "f1") {
            action.subscribe(this)
        }
    }
    open override fun actionOccurred(sender: IAction, time: DateTime) {
        updateInteractionsCount()

        // TODO: Calculate number of people, who bought the software
        // TODO: Test this
        buyersCount = subscribersBuy()
        // TODO: Test this
    }

    open fun subscribersBuy(): Int {
        val potentialBuyers = this.subscribers.filter { (it.interactionsWithStacy >= 7) && !it.boughtSomething }
        val indices = getIndicesOfSubscribersToUpdate(potentialBuyers, 0.1)
        indices.forEach { this.subscribers.get(it).boughtSomething = true }
        return indices.size
    }

    open fun updateInteractionsCount() {
        val processedIndices = getIndicesOfSubscribersToUpdate(subscribers, 0.5)
        processedIndices.forEach { this.subscribers.get(it).interactionsWithStacy++ }
    }

    private fun getIndicesOfSubscribersToUpdate(parameter: Collection<Subscriber>,
                                                percentage: Double): ArrayList<Int> {
        val readersCount = (parameter.size * percentage).toInt()
        val processedIndices = ArrayList<Int>(readersCount)

        for (i in 1..readersCount) {
            var readerIndex = random.nextInt(parameter.size)
            while (processedIndices.contains(readerIndex)) {
                readerIndex = random.nextInt(parameter.size)
            }
            processedIndices.add(readerIndex)
        }
        return processedIndices
    }
}