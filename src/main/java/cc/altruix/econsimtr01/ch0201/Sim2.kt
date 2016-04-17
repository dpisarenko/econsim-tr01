package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*

/**
 * Created by pisarenko on 13.04.2016.
 */
class Sim2(val logTarget:StringBuilder,
           val flows:MutableList<ResourceFlow>,
           simParametersProvider: Sim2ParametersProvider) : DefaultSimulation(Timing(), simParametersProvider) {
    override fun continueCondition(tick: Long): Boolean {
        val t = tick.secondsToSimulationDateTime()
        return (t.monthOfYear <= 12)
    }

    override fun createAgents(): List<IAgent> {
        attachFlowsToAgents(
                (simParametersProvider as Sim1ParametersProvider).flows,
                simParametersProvider.agents,
                this.flows)
        setInitialResourceLevels()
        setInfiniteResourceSupplies()
        return simParametersProvider.agents
    }

    override fun setInitialResourceLevels() {
        simParametersProvider.initialResourceLevels.forEach { initialResourceLevel ->
            val agent = findAgent(initialResourceLevel.agent)
            if ((agent != null) && (agent is DefaultAgent)) {
                agent.put(initialResourceLevel.resource, initialResourceLevel.amt)
            } else {
                LOGGER.error("Can't find agent '${initialResourceLevel.agent}'")
            }
        }
    }


    override fun createSensors(): List<ISensor> =
            listOf(
                    Sim2Accountant(
                            logTarget,
                            simParametersProvider.agents,
                            (simParametersProvider as Sim2ParametersProvider).resources
                    )
            )
}
