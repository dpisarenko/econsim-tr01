package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.IAction
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
            val resultsStorage:Map<DateTime,Sim1aResultsRow>) :
        Sim1(logTarget, flows, simParametersProvider) {
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
        // TODO: Test this
        return listOf(Sim1aAccountant(resultsStorage))
    }
}