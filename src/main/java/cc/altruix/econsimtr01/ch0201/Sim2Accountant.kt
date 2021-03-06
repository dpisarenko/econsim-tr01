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

import cc.altruix.econsimtr01.AbstractAccountant
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.PlResource
import cc.altruix.econsimtr01.newLine
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by pisarenko on 14.04.2016.
 */
open class Sim2Accountant(logTarget: StringBuilder,
                     agents: List<IAgent>,
                     resources: List<PlResource>) : AbstractAccountant(logTarget, agents, resources) {
    companion object {
        val cohortResources = hashMapOf(
                Pair(1, "r06-pc1"),
                Pair(2, "r07-pc2"),
                Pair(3, "r08-pc3"),
                Pair(4, "r09-pc4"),
                Pair(5, "r10-pc5"),
                Pair(6, "r11-pc6"),
                Pair(7, "r12-pc7")
        )
        val inverseCohortResources =
                createInverseCohortResources(cohortResources)

        private fun createInverseCohortResources(cohortResources: HashMap<Int, String>): Map<String,Int> {
            val map = HashMap<String,Int>()
            cohortResources.forEach { interactions, id ->
                map.put(id, interactions)
            }
            return map
        }

    }
    val LOGGER = LoggerFactory.getLogger(Sim2Accountant::class.java)
    override fun logStockLevels(time: Long) {
        logNormalStockLevels(time)
        logCohortData(time)
    }

    open internal fun logNormalStockLevels(time: Long) {
        agents.forEach { agent ->
            resources
                    .filter { !cohortResources.values.contains(it.id) }
                    .forEach { appendResourceAmount(time, agent, it) }
        }
    }

    open internal fun logCohortData(time: Long) {
        val list = findList()
        if (list == null) {
            LOGGER.error("Can't find list agent.")
            return
        }
        val subscribersCountByNumberOfInteractions =
                calculateSubscribersCountByNumberOfInteractions(list)

        logSubscribersCountByNumberOfInteractions(list, subscribersCountByNumberOfInteractions, time, logTarget)
    }

    open internal fun logSubscribersCountByNumberOfInteractions(
            list: ListAgent,
            subscribersCountByNumberOfInteractions: HashMap<Int, AtomicInteger>,
            time: Long,
            log: StringBuilder) {
        subscribersCountByNumberOfInteractions.map {
            Pair(cohortResources.get(it.key), it.value.get().toString())
        }.forEach {
            log.append("resourceLevel($time, '${list.id()}', '${it.first}', ${it.second}).")
            log.newLine()
        }
    }

    open internal fun findList() = this.agents.filter { it is ListAgent }.firstOrNull() as ListAgent

    open internal fun calculateSubscribersCountByNumberOfInteractions(list: ListAgent): HashMap<Int, AtomicInteger> {
        val subscribersCountByNumberOfInteractions =
                createSubscribersCountByNumberOfInteractions()
        list.subscribers.forEach {
            var itemToIncrement: AtomicInteger? = findItemToIncrement(subscribersCountByNumberOfInteractions,
                    it.interactionsWithStacy)
            if (itemToIncrement == null) {
                LOGGER.error("Couldn't find cohort for a subscriber with ${it.interactionsWithStacy} interactions")
            } else {
                itemToIncrement.incrementAndGet()
            }
        }
        return subscribersCountByNumberOfInteractions
    }

    internal fun findItemToIncrement(subscribersCountByNumberOfInteractions: HashMap<Int, AtomicInteger>,
                                     interactionsWithStacy: Int): AtomicInteger? {
        var itemToIncrement: AtomicInteger? = null
        when (interactionsWithStacy) {
            in 1..7 -> {
                itemToIncrement =
                        subscribersCountByNumberOfInteractions.get(interactionsWithStacy)
            }
            else -> {
                itemToIncrement =
                        subscribersCountByNumberOfInteractions.get(7)
            }
        }
        return itemToIncrement
    }

    internal fun createSubscribersCountByNumberOfInteractions(): HashMap<Int, AtomicInteger> {
        return hashMapOf(
                Pair(1, AtomicInteger(0)),
                Pair(2, AtomicInteger(0)),
                Pair(3, AtomicInteger(0)),
                Pair(4, AtomicInteger(0)),
                Pair(5, AtomicInteger(0)),
                Pair(6, AtomicInteger(0)),
                Pair(7, AtomicInteger(0))
        )
    }
}
