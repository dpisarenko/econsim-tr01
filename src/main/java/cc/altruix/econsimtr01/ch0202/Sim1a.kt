package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.IAction
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ISensor
import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.ch0201.OncePerWeek
import org.joda.time.DateTime

/**
 * Created by pisarenko on 04.05.2016.
 */
class Sim1a(logTarget:StringBuilder,
            flows:MutableList<ResourceFlow>,
            simParametersProvider: Sim1ParametersProvider,
            val resultsStorage:MutableMap<DateTime,Sim1aResultsRow>) :
        Sim1(logTarget, flows, simParametersProvider) {
    override fun createAgents(): List<IAgent> {
        val agents = super.createAgents()
        attachFlowsToAgents(
                (simParametersProvider as Sim1ParametersProvider).flows,
                simParametersProvider.agents,
                this.flows)

        return agents
    }

    override fun createUnattachedProcesses(): List<IAction> {
        val sim1Params = simParametersProvider as Sim1ParametersProvider
        return listOf(
                IntroductionProcess(
                population = population,
                triggerFun = OncePerWeek("Monday"),
                averageNetworkActivity =
                    sim1Params.averageNetworkActivity,
                averageSuggestibilityOfStrangers =
                    sim1Params.averageSuggestibilityOfStrangers
                )
        )
    }
    override fun createSensors(): List<ISensor> {
        return listOf(
                Sim1aAccountant(
                        resultsStorage,
                        (simParametersProvider as Sim1ParametersProvider).name
                )
        )
    }
}