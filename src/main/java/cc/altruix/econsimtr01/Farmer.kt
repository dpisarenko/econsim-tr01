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
class Farmer(foodStorage: IResourceStorage,
             flows: MutableList<ResourceFlow>,
             val maxDaysWithoutFood: Int,
             dailyPotatoConsumption: Double) : IAgent, IHuman {
    override fun id(): String = "Farmer"

    val actions = listOf<IAction>(
            EatingAction(
                this,
                foodStorage,
                flows,
                dailyPotatoConsumption
            )
    )
    var daysWithoutFood : Int = 0
    var alive: Boolean = true
        get
        private set
    override fun eat() {
        this.daysWithoutFood = 0
    }

    override fun hungerOneDay() {
        this.daysWithoutFood++
        if (this.daysWithoutFood > maxDaysWithoutFood) {
            die()
        }
    }

    override fun die() {
        alive = false
    }
    override fun act(time: DateTime) {
        actions
                .filter { x -> x.timeToRun(time) }
                .forEach { x -> x.run(time) }
    }
}
