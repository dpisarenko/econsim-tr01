package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.slf4j.LoggerFactory
import java.util.*

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
        (simParametersProvider as Sim1ParametersProvider).flows.forEach { flow ->
            attachFlowToAgent(
                    simParametersProvider.agents,
                    flow,
                    flows
            )
        }
        setInitialResourceLevels()
        setInfiniteResourceSupplies()

        return simParametersProvider.agents
    }

    protected fun setInfiniteResourceSupplies() {
        simParametersProvider.infiniteResourceSupplies.forEach { infiniteResourceSupply ->
            val agent = findAgent(infiniteResourceSupply.agent)
            if (agent is DefaultAgent) {
                agent.setInfinite(infiniteResourceSupply.res)
            }
        }
    }

    protected fun setInitialResourceLevels() {
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
                    Sim1Accountant(
                            logTarget,
                            simParametersProvider.agents,
                            (simParametersProvider as Sim1ParametersProvider).resources
                    )
            )
}