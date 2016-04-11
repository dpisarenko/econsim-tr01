package cc.altruix.econsimtr01

import cc.altruix.econsimtr01.ch0201.Sim1
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 09.04.2016.
 */
class PlFlow(val id:String,
                  val src: String,
                  val target:String,
                  val resource:String,
                  val amount:Double?,
                  val timeTriggerFunction: (DateTime) -> Boolean) : IAction {
    val LOGGER = LoggerFactory.getLogger(PlFlow::class.java)

    lateinit var agents:List<IAgent>
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
            }
        } else {
            LOGGER.error("Agent '$targetAgent' isn't a resource storage")
        }
    }

    private fun findAgent(id: String) = agents.filter { x -> x.id().equals(id) }.first()

}
