/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 */

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.IResourceLevelObserver
import cc.altruix.econsimtr01.findAgent
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 20.04.2016.
 */
open class WhenResourceReachesLevel(val agent:String,
                               val resource:String,
                               val amount:Double) : (DateTime) -> Boolean,
        IResourceLevelObserver {

    val LOGGER = LoggerFactory.getLogger(WhenResourceReachesLevel::class.java)
    var nextFireTime:DateTime? = null
    override fun invoke(time: DateTime): Boolean {
        val fire = ((nextFireTime != null) &&  time.isEqual(nextFireTime))
        if (fire) {
            reset()
        }
        return fire
    }
    private fun reset() {
        nextFireTime = null
    }
    open fun connectToInitiatingAgent(agents:List<IAgent>) {
        val agent = findAgent(agent, agents)
        if (agent == null) {
            LOGGER.error("Can't find agent '$agent'")
            return
        }
        if (agent is DefaultAgent) {
            agent.addResourceLevelObserver(this)
        }
    }
    override fun possibleResourceLevelChange(agent: DefaultAgent, time: DateTime) {
        val currentAmount = agent.amount(resource)
        if (currentAmount >= amount) {
            this.nextFireTime = time.plusMinutes(1)
        }
    }
}