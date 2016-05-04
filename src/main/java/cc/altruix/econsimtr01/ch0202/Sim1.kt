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
        return listOf(
                Protagonist(sim1Params.totalTimeForOfflineNetworkingPerWeek,
                        sim1Params.maxNetworkingSessionsPerBusinessDay,
                        sim1Params.timePerOfflineNetworkingSession,
                        sim1Params.recommendationConversion,
                        sim1Params.willingnessToPurchaseConversion,
                        population
                )
        )
    }

    override fun createSensors(): List<ISensor> {
        // TODO: Implement this
        // TODO: Test this
        // TBD
        return emptyList()
    }
}
