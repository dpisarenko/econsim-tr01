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

package cc.altruix.econsimtr01

import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 04.04.2016.
 */
abstract class DefaultSimulation(val simParametersProvider: ISimParametersProvider) : ISimulation {
    val LOGGER = LoggerFactory.getLogger(DefaultSimulation::class.java)
    override fun run() {
        val agents = createAgents()
        val sensors = createSensors()
        val unattachedProcesses = createUnattachedProcesses()
        var time = 0L.millisToSimulationDateTime()
        while (continueCondition(time)) {
            time = minimalSimulationCycle(agents, sensors, time, unattachedProcesses)
        }
    }

    internal open fun minimalSimulationCycle(agents: List<IAgent>,
                                        sensors: List<ISensor>,
                                        oldTime: DateTime,
                                        unattachedProcesses: List<IAction>)
            : DateTime {
        val newTime = oldTime.plusMinutes(1)
        agents.forEach { it.act(newTime) }
        unattachedProcesses.forEach { it.run(newTime) }
        sensors.forEach { it.measure(newTime, agents) }
        return newTime
    }
    internal abstract fun continueCondition(tick: DateTime): Boolean
    internal open abstract fun createAgents(): List<IAgent>
    internal open abstract fun createSensors(): List<ISensor>
    internal open fun createUnattachedProcesses():List<IAction> = emptyList()
    fun findAgent(agentId: String) =
            simParametersProvider.agents.filter { x -> x.id().equals(agentId) }.firstOrNull()

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
