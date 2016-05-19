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
    val subscribers : MutableList<IActionSubscriber> = LinkedList()

    override fun timeToRun(time: DateTime): Boolean = timeTriggerFunction(time)
    override fun run(time: DateTime) {
        run(amount, time)
    }

    open fun run(amount: Double?, time: DateTime) {
        val targetAgent = findAgent(target, agents)
        val srcAgent = findAgent(src, agents)

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



    open fun addFollowUpFlow(nextTrigger: After) {
        followingTriggers.add(nextTrigger)
    }

    override fun notifySubscribers(time: DateTime) {
        this.subscribers.forEach { it.actionOccurred(this, time) }
    }

    override fun subscribe(subscriber: IActionSubscriber) {
        this.subscribers.add(subscriber)
    }
}
