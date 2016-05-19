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

package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime

open class Sim1(val logTarget:StringBuilder,
           val flows:MutableList<ResourceFlow>,
           simParametersProvider: Sim1ParametersProvider) : DefaultSimulation(simParametersProvider) {

    override fun continueCondition(time: DateTime): Boolean = time.year <= 1

    val population = Population(simParametersProvider.initialNetworkSize)
    override fun createAgents(): List<IAgent> {
        val sim1Params = simParametersProvider as Sim1ParametersProvider
        val agents = listOf(
                Protagonist(sim1Params.totalTimeForOfflineNetworkingPerWeek,
                        sim1Params.maxNetworkingSessionsPerBusinessDay,
                        sim1Params.timePerOfflineNetworkingSession,
                        sim1Params.recommendationConversion,
                        sim1Params.willingnessToPurchaseConversion,
                        population
                ),
                Nature()
        )
        return agents
    }

    override fun createSensors(): List<ISensor> {
        return emptyList()
    }
}
