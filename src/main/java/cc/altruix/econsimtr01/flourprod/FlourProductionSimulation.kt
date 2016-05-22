package cc.altruix.econsimtr01.flourprod

import cc.altruix.econsimtr01.DefaultSimulation
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ISensor
import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.ch0202.SimResRow
import cc.altruix.econsimtr01.ch03.AgriculturalSimulationRowField
import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class FlourProductionSimulation(val logTarget:StringBuilder,
                                val flows:MutableList<ResourceFlow>,
                                simParametersProvider:
                                FlourProductionSimulationParametersProvider,
                                val resultsStorage:MutableMap<DateTime,
                                    SimResRow<AgriculturalSimulationRowField>>)
: DefaultSimulation(simParametersProvider){
    override fun continueCondition(time: DateTime): Boolean = time.year <= 3

    override fun createAgents(): List<IAgent> = simParametersProvider.agents

    override fun createSensors(): List<ISensor> =
        listOf(FlourProductionSimulationAcountant(resultsStorage,
            (simParametersProvider as
                FlourProductionSimulationParametersProvider)
                .data["SimulationName"].toString()))
}
