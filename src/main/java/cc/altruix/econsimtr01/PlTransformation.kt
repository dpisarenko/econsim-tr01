package cc.altruix.econsimtr01

import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import java.util.*

/**
 * Created by pisarenko on 19.04.2016.
 */
open class PlTransformation(val id:String,
                       val agentId:String,
                       val inputAmount:Double,
                       val inputResourceId:String,
                       val outputAmount:Double,
                       val outputResourceId:String,
                       val timeTriggerFunction: (DateTime) -> Boolean) : IAction {
    val LOGGER = LoggerFactory.getLogger(PlTransformation::class.java)
    val subscribers : MutableList<IActionSubscriber> = LinkedList<IActionSubscriber>()

    // TODO: Make sure agents property is initialized
    lateinit var agents:List<IAgent>

    // TODO: Make sure agents property is initialized
    lateinit var flows:MutableList<ResourceFlow>

    override fun timeToRun(time: DateTime): Boolean = timeTriggerFunction(time)

    override fun run(time: DateTime) {
        if (inputAmount == null) {
            LOGGER.error("Input amount is null")
            return
        }

        if (outputAmount == null) {
            LOGGER.error("Output amount is null")
            return
        }

        val agent = findAgent()

        if (agent == null) {
            LOGGER.error("Can't find agent '$agentId'")
            return
        }

        if (agent !is IResourceStorage) {
            LOGGER.error("Agent '$agentId' isn't a resource storage")
            return
        }
        val availableAmount = agent.amount(inputResourceId)
        if (!agent.isInfinite(inputResourceId) && (availableAmount < inputAmount)) {
            LOGGER.error("Quantity of $inputResourceId at $agentId ($availableAmount) is less than required amount of $inputAmount")
        } else {
            agent.remove(inputResourceId, inputAmount)
            agent.put(outputResourceId, outputAmount)
        }
    }

    open internal fun findAgent() = findAgent(agentId, agents)

    override fun notifySubscribers(time: DateTime) {
        this.subscribers.forEach { it.actionOccurred(this, time) }
    }

    override fun subscribe(subscriber: IActionSubscriber) {
        this.subscribers.add(subscriber)
    }
}