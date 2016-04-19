package cc.altruix.econsimtr01

import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 04.04.2016.
 */
abstract class DefaultSimulation(val simParametersProvider: ISimParametersProvider) : ISimulation {
    val LOGGER = LoggerFactory.getLogger(DefaultSimulation::class.java)
    override fun run():SimResults {
        val results = SimResults()
        val agents = createAgents()
        val sensors = createSensors()
        var time = 0L.millisToSimulationDateTime()
        while (continueCondition(time)) {
            time = minimalSimulationCycle(agents, sensors, time)
        }
        return results
    }

    internal fun minimalSimulationCycle(agents: List<IAgent>,
                                        sensors: List<ISensor>,
                                        oldTime: DateTime): DateTime {
        val newTime = oldTime.plusMinutes(1)
        agents.forEach { it.act(newTime) }
        sensors.forEach { it.measure(newTime) }
        return newTime
    }

    internal abstract fun continueCondition(tick: DateTime): Boolean
    internal abstract fun createAgents(): List<IAgent>
    protected abstract fun createSensors(): List<ISensor>
    fun findAgent(agentId: String) =
            simParametersProvider.agents.filter { x -> x.id().equals(agentId) }.first()

    protected fun attachFlowToAgent(agents: List<IAgent>,
                                    flow: PlFlow,
                                    flows:MutableList<ResourceFlow>) {
        val agent = simParametersProvider.agents
                .filter { x -> x.id().equals(flow.src) }
                .firstOrNull()
        if (agent == null) {
            LOGGER.error("Can't find agent ${flow.src}")
        } else {
            flow.agents = agents
            flow.flows = flows

            if (agent is DefaultAgent) {
                agent.addAction(flow)
            }
        }
    }

    open internal fun attachFlowsToAgents(plFlows: MutableList<PlFlow>,
                                      agents: MutableList<IAgent>,
                                      flows: MutableList<ResourceFlow>) {
        plFlows.forEach { flow ->
            attachFlowToAgent(
                    agents,
                    flow,
                    flows
            )
        }
    }

    open internal fun setInfiniteResourceSupplies() {
        simParametersProvider.infiniteResourceSupplies.forEach { infiniteResourceSupply ->
            val agent = findAgent(infiniteResourceSupply.agent)
            if (agent is DefaultAgent) {
                agent.setInfinite(infiniteResourceSupply.res)
            }
        }
    }

    open internal fun setInitialResourceLevels() {
        simParametersProvider.initialResourceLevels
                .forEach { initialResourceLevel ->
            val agent = findAgent(initialResourceLevel.agent)
            if ((agent != null) && (agent is DefaultAgent)) {
                agent.put(initialResourceLevel.resource, initialResourceLevel.amt)
            } else {
                LOGGER.error("Can't find agent '${initialResourceLevel.agent}'")
            }
        }
    }
}
