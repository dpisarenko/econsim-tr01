package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*

/**
 * Created by pisarenko on 08.04.2016.
 */
class Sim1(val logTarget:StringBuilder,
            val flows:MutableList<ResourceFlow>,
            simParametersProvider: Sim1ParametersProvider) :
        DefaultSimulation(Timing(),
                simParametersProvider) {
    override fun continueCondition(tick:Long): Boolean {
        val t = tick.secondsToSimulationDateTime()
        return (t.monthOfYear <= 3)
    }

    override fun createAgents(): MutableList<IAgent> {
        attachFlowsToAgents(
                (simParametersProvider as Sim1ParametersProvider).flows,
                simParametersProvider.agents,
                this.flows)
        setInitialResourceLevels()
        setInfiniteResourceSupplies()

        return simParametersProvider.agents
    }

    override fun createSensors(): List<ISensor> =
            listOf(
                    Sim1Accountant(
                            logTarget,
                            simParametersProvider.agents,
                            (simParametersProvider as Sim1ParametersProvider).resources
                    )
            )
}
