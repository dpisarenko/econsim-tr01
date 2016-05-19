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

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime

/**
 * Created by pisarenko on 19.04.2016.
 */
open class Sim4(val logTarget:StringBuilder,
           val flows:MutableList<ResourceFlow>,
           simParametersProvider: Sim4ParametersProvider) :
    DefaultSimulation(simParametersProvider){
    override fun continueCondition(time: DateTime): Boolean = (time.year <= 1)

    override fun createAgents(): List<IAgent> {
        attachFlowsToAgents(
                simParametersProvider.flows,
                simParametersProvider.agents,
                this.flows)
        attachTransformationsToAgents(
                simParametersProvider.transformations,
                simParametersProvider.agents
        )
        setInitialResourceLevels()
        setInfiniteResourceSupplies()
        return simParametersProvider.agents
    }

    internal open fun attachTransformationsToAgents(
            trs: MutableList<PlTransformation>,
            agents: List<IAgent>) {
        trs.forEach { attachTransformationToAgent(agents, it) }
    }

    internal open fun attachTransformationToAgent(agents: List<IAgent>, tr: PlTransformation) {
        val agent = findAgent(agents, tr.agentId)
        if (agent == null) {
            LOGGER.error("Can't find agent ${tr.agentId}")
            return
        }
        tr.agents = agents
        if (agent is DefaultAgent) {
            agent.addTransformation(tr)
        }
    }

    open internal fun findAgent(agents: List<IAgent>, agentId: String) =
            agents.filter { it.id() == agentId }.firstOrNull()

    override fun createSensors(): List<ISensor> =
            listOf(Sim4Accountant(
                    logTarget,
                    simParametersProvider.agents,
                    (simParametersProvider as Sim4ParametersProvider).resources)
            )
}