package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.PlFlow
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 14.04.2016.
 */
open class After(val flowId:String) : (DateTime) -> Boolean {
    val LOGGER = LoggerFactory.getLogger(After::class.java)
    var nextFireTime:DateTime? = null

    init {
        reset()
    }

    private fun reset() {
        nextFireTime = null
    }

    override fun invoke(p1: DateTime): Boolean {
        val fire = ((nextFireTime != null) &&  p1.isEqual(nextFireTime))
        if (fire) {
            reset()
        }
        return fire
    }

    fun connectToInitiatingFunctionFlow(flows:MutableList<PlFlow>) {
        val initiatingFlow = flows.filter { flowId.equals(it.id) }.firstOrNull()
        if (initiatingFlow != null) {
            initiatingFlow.addFollowUpFlow(this)
        } else {
            LOGGER.error("Can't find flow '$flowId'")
        }
    }
    open fun updateNextFiringTime(time: DateTime) {
        this.nextFireTime = time.plusMinutes(1)
    }
}
