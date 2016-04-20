package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.findAgent
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 20.04.2016.
 */
open class WhenResourceReachesLevel(val agent:String,
                               val resource:String,
                               val amount:Double) : (DateTime) -> Boolean {
    val LOGGER = LoggerFactory.getLogger(WhenResourceReachesLevel::class.java)
    override fun invoke(time: DateTime): Boolean {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }
    open fun connectToInitiatingAgent(agents:List<IAgent>) {
        // TODO: Implement this
        // TODO: Test this
        val agent = findAgent(agent, agents)
        if (agent == null) {
            LOGGER.error("Can't find agent '$agent'")
            return
        }
    }
}