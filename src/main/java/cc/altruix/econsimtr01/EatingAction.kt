/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class EatingAction(val recipient: Farmer,
                   val foodStorage: IResourceStorage,
                   val flows: MutableList<ResourceFlow>,
                   val dailyPotatoConsumption: Double) :
        DefaultAction(composeHourMinuteFiringFunction(20, 0)) {

    override fun run(time: DateTime) {
        if (recipient.alive) {
            eatIfPossible(time, dailyPotatoConsumption)
        }
    }

    private fun eatIfPossible(time: DateTime, amount: Double) {
        val potatoes = this.foodStorage.amount(Resource.POTATO.name)
        if (potatoes < amount) {
            recipient.hungerOneDay()
        } else {
            this.foodStorage.remove(Resource.POTATO.name, amount)
            this.flows.add(ResourceFlow(time, foodStorage, recipient, Resource.POTATO.name, amount))
            recipient.eat()
        }
    }

    override fun notifySubscribers(time: DateTime) {
    }
    override fun subscribe(subscriber: IActionSubscriber) {
    }
}
