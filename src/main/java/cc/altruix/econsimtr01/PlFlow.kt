package cc.altruix.econsimtr01

import cc.altruix.econsimtr01.ch0201.After
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import java.util.*

/**
 * Created by pisarenko on 09.04.2016.
 */
open class PlFlow(val id:String,
                  val src: String,
                  val target:String,
                  val resource:String,
                  val amount:Double?,
                  val timeTriggerFunction: (DateTime) -> Boolean) : IAction {
    val LOGGER = LoggerFactory.getLogger(PlFlow::class.java)

    val followingTriggers : MutableList<After> = LinkedList()
    lateinit var agents:List<IAgent>

    lateinit var flows:MutableList<ResourceFlow>
    override fun timeToRun(time: DateTime): Boolean = timeTriggerFunction(time)

    override fun run(time: DateTime) {
        val targetAgent = findAgent(target)
        val srcAgent = findAgent(src)

        if (targetAgent == null) {
            LOGGER.error("Can't find agent $target")
            return
        }
        if (srcAgent == null) {
            LOGGER.error("Can't find agent $src")
            return
        }

        if ((targetAgent is IResourceStorage) && (srcAgent is IResourceStorage)) {
            if (amount != null) {
                val availableAmount = srcAgent.amount(resource)
                if (!srcAgent.isInfinite(resource) && (availableAmount < amount)) {
                    LOGGER.error("Quantity of $resource at $src ($availableAmount) is less than flow amount of $amount")
                } else {
                    srcAgent.remove(resource, amount)
                    targetAgent.put(resource, amount)
                    addFlow(srcAgent, targetAgent, time)
                }
            } else {
                addFlow(srcAgent, targetAgent, time)
            }
        } else {
            LOGGER.error("Agent '$targetAgent' isn't a resource storage")
        }
    }

    fun addFlow(srcAgent: IAgent, targetAgent: IAgent, time: DateTime) {
        flows.add(ResourceFlow(time, srcAgent, targetAgent, resource, amount))
        followingTriggers.forEach { it.updateNextFiringTime(time) }
    }

    private fun findAgent(id: String) = agents.filter { x -> x.id().equals(id) }.first()

    open fun addFollowUpFlow(nextTrigger: After) {
        followingTriggers.add(nextTrigger)
    }

    override fun notifySubscribers(time: DateTime) {
        // TODO: Implement this
        // TODO: Test this
    }
}
