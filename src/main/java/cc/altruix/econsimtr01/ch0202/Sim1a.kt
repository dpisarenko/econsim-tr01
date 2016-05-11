package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.*
import cc.altruix.econsimtr01.ch0201.OncePerWeek
import org.joda.time.DateTime
import java.util.*

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
        val plFlows = LinkedList<PlFlow>()
        agents.forEach { agt ->
            if (agt is DefaultAgent) {
                agt.actions
                        .filter { it is PlFlow }
                        .forEach { plFlows.add(it as PlFlow) }
            }
        }
        plFlows.forEach { flow ->
            flow.agents = agents
            flow.flows = flows
        }
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
