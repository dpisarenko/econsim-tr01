/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
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