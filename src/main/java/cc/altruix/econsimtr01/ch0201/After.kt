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
        // TODO: Test this
        val fire = ((nextFireTime != null) &&  p1.isEqual(nextFireTime))
        if (fire) {
            reset()
        }
        /*
        if (isMidnight(p1)) {
            reset()
        }
        */
        // return p1.millis == nextFireTime
        return fire
    }

    fun isMidnight(t: DateTime): Boolean =
            arrayOf(t.hourOfDay, t.minuteOfHour, t.secondOfMinute).all { it == 0 }

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
