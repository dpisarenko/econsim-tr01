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

        logSubscribersCountByNumberOfInteractions(list, subscribersCountByNumberOfInteractions, time)
    }

    open internal fun logSubscribersCountByNumberOfInteractions(list: ListAgent,
                                                           subscribersCountByNumberOfInteractions: HashMap<Int, AtomicInteger>, time: Long) {
        // TODO: Test this
        subscribersCountByNumberOfInteractions.map {
            Pair(cohortResources.get(it.key), it.value.get().toString())
        }.forEach {
            logTarget.append("resourceLevel($time, '${list.id()}', '${it.first}', ${it.second}).")
            logTarget.newLine()
        }
    }

    open internal fun findList() = this.agents.filter { it is ListAgent }.firstOrNull() as ListAgent

    open internal fun calculateSubscribersCountByNumberOfInteractions(list: ListAgent): HashMap<Int, AtomicInteger> {
        // TODO: Test this
        val subscribersCountByNumberOfInteractions =
                createSubscribersCountByNumberOfInteractions()
        list.subscribers.forEach {
            var itemToIncrement: AtomicInteger? = findItemToIncrement(subscribersCountByNumberOfInteractions, it.interactionsWithStacy)
            if (itemToIncrement == null) {
                LOGGER.error("Couldn't find cohort for a subscriber with ${it.interactionsWithStacy} interactions")
            } else {
                itemToIncrement.incrementAndGet()
            }
        }
        return subscribersCountByNumberOfInteractions
    }

    internal fun findItemToIncrement(subscribersCountByNumberOfInteractions: HashMap<Int, AtomicInteger>, interactionsWithStacy: Int): AtomicInteger? {
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
