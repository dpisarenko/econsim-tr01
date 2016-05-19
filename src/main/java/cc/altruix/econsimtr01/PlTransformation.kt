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
    val subscribers : MutableList<IActionSubscriber> = LinkedList()

    lateinit var agents:List<IAgent>

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