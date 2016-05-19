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

package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Simulation1(val logTarget:StringBuilder,
                  val flows:MutableList<ResourceFlow>,
                  simParametersProvider: SimParametersProvider) : DefaultSimulation(simParametersProvider) {
    val foodStorage = DefaultResourceStorage("FoodStorage")
    val farmer = Farmer(
            foodStorage,
            flows,
            simParametersProvider.maxDaysWithoutFood,
            simParametersProvider.dailyPotatoConsumption
    )

    override fun createSensors(): List<ISensor> =
            listOf(
                    Accountant(
                            foodStorage,
                            farmer,
                            logTarget)
            )

    override fun createAgents(): List<IAgent> {
        foodStorage.put(
                Resource.POTATO.name,
                (simParametersProvider as SimParametersProvider).initialAmountOfPotatoes
        )
        val agents = listOf(
                farmer,
                Field(),
                Nature()
        )
        return agents
    }

    override fun continueCondition(tick: DateTime): Boolean = farmer.alive
}
