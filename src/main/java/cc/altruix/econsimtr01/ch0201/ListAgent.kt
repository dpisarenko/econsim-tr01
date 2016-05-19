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

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import java.util.*

/**
 * Created by pisarenko on 14.04.2016.
 */
open class ListAgent(id:String,
                     val percentageOfReaders:Double = 0.5,
                     val interactionsBeforePurchase:Int = 7,
                     val percentageOfBuyers:Double = 0.1) : DefaultAgent(id), IActionSubscriber {
    val LOGGER = LoggerFactory.getLogger(ListAgent::class.java)
    companion object {
        val subscriberTypes = hashMapOf(
                Pair("r06-pc1", 1),
                Pair("r07-pc2", 2),
                Pair("r08-pc2", 3),
                Pair("r09-pc2", 4),
                Pair("r10-pc2", 5),
                Pair("r11-pc2", 6),
                Pair("r12-pc2", 7)
        )
    }
    val random = createRandom()
    val subscribers : MutableList<Subscriber> = LinkedList()
    var buyersCount : Int = 0
    override fun put(res: String, amt: Double) {
        if (subscriberTypes.containsKey(res)) {
            addSubscribers(res, amt)
        } else {
            super.put(res, amt)
        }
    }

    open fun addSubscribers(res: String, amt: Double) {
        if (!subscriberTypes.containsKey(res)) {
            LOGGER.error("Unknown subscriber type '$res'")
            return
        }
        val number = Math.ceil(amt).toInt()
        val interactions = subscriberTypes.get(res) as Int
        for (i in 1..number) {
            subscribers.add(Subscriber(UUID.randomUUID().toString(), interactions))
        }
    }

    open override fun addAction(action: PlFlow) {
        super.addAction(action)
        if (action.id == "f1") {
            action.subscribe(this)
        }
    }
    open override fun actionOccurred(sender: IAction, time: DateTime) {
        updateInteractionsCount()
        buyersCount = subscribersBuy()
    }

    open fun subscribersBuy(): Int {
        val potentialBuyers = calculatePotentialBuyers()
        val indices = getIndicesOfSubscribersToUpdate(potentialBuyers, percentageOfBuyers)
        updateBoughtSomethingProperty(indices, potentialBuyers)
        return indices.size
    }

    open internal fun calculatePotentialBuyers(): List<Subscriber> {
        return this.subscribers.filter {
            (it.interactionsWithStacy >= interactionsBeforePurchase) &&
                    (!it.boughtSomething)
        }
    }

    open internal fun updateBoughtSomethingProperty(indices: List<Int>, potentialBuyers: List<Subscriber>) {
        indices.forEach { potentialBuyers.get(it).boughtSomething = true }
    }

    open fun updateInteractionsCount() {
        val processedIndices = getIndicesOfSubscribersToUpdate(subscribers, percentageOfReaders)
        processedIndices.forEach {
            val old = this.subscribers.get(it).interactionsWithStacy
            val new = old + 1
            this.subscribers.get(it).interactionsWithStacy = new
        }
    }

    open internal fun getIndicesOfSubscribersToUpdate(
            subscribers: List<Subscriber>,
            percentage: Double): List<Int> {
        val readersCount = (subscribers.size * percentage).toInt()
        val processedIndices = ArrayList<Int>(readersCount)

        for (i in 1..readersCount) {
            var readerIndex = random.nextInt(subscribers.size)
            while (processedIndices.contains(readerIndex)) {
                readerIndex = random.nextInt(subscribers.size)
            }
            processedIndices.add(readerIndex)
        }
        return processedIndices
    }
}
