package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.PlFlow
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 14.04.2016.
 */
open class After(val flowId:String) : (DateTime) -> Boolean {
    val LOGGER = LoggerFactory.getLogger(After::class.java)
    var nextFireTime:Long = -1

    init {
        reset()
    }

    private fun reset() {
        nextFireTime = -1
    }

    override fun invoke(p1: DateTime): Boolean {
        if (isMidnight(p1)) {
            // TODO: Test this
            reset()
        }
        return p1.millis == nextFireTime
    }

    fun isMidnight(t: DateTime): Boolean =
            arrayOf(t.hourOfDay, t.minuteOfHour, t.secondOfMinute).all { it == 0 }

    fun connectToInitiatingFunctionFlow(flows:MutableList<PlFlow>) {
        // TODO: Test this
        val initiatingFlow = flows.filter { flowId.equals(it.id) }.first()
        if (initiatingFlow != null) {
            initiatingFlow.addFollowUpFlow(this)
        } else {
            LOGGER.error("Can't find flow '$flowId'")
        }
    }

    open fun updateNextFiringTime(time: DateTime) {
        this.nextFireTime = time.millis + 1
    }
}