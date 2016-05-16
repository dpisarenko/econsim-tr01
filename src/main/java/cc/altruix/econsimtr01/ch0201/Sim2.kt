/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime
import java.util.*

/**
 * Created by pisarenko on 13.04.2016.
 */
open class Sim2(val logTarget:StringBuilder,
           val flows:MutableList<ResourceFlow>,
           simParametersProvider: Sim2ParametersProvider) : DefaultSimulation(simParametersProvider) {
    override fun continueCondition(time: DateTime): Boolean = (time.year == 0)

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
        simParametersProvider.initialResourceLevels
                .forEach { initialResourceLevel ->
            val agent = findAgent(initialResourceLevel.agent)
            setInitialResourceLevel(
                    agent,
                    initialResourceLevel)
        }
    }

    internal fun setInitialResourceLevel(agent: IAgent?,
                                         initialResourceLevel: InitialResourceLevel) {
        if ((agent != null) && (agent is ListAgent) &&
                Sim2Accountant.cohortResources.values.contains(initialResourceLevel.resource)) {
            addSubscribers(agent, initialResourceLevel)
        } else  if ((agent != null) &&
                (agent is DefaultAgent) &&
                !Sim2Accountant.cohortResources.values.contains(initialResourceLevel.resource)) {
            setInitialResourceLevel2(agent, initialResourceLevel)
        } else {
            LOGGER.error("Can't find agent '${initialResourceLevel.agent}'")
        }
    }

    internal open fun addSubscribers(list: ListAgent,
                                     initialResourceLevel: InitialResourceLevel) {
        val interactions = Sim2Accountant.inverseCohortResources.get(initialResourceLevel.resource)
        if (interactions == null) {
            LOGGER.error("Can't determine interactions for resource '${initialResourceLevel.resource}'")
            return
        }
        for (i in 1..initialResourceLevel.amt.toInt()) {
            list.subscribers.add(Subscriber(UUID.randomUUID().toString(), interactions))
        }
    }

    open internal fun setInitialResourceLevel2(agent: DefaultAgent, initialResourceLevel: InitialResourceLevel) {
        agent.put(initialResourceLevel.resource, initialResourceLevel.amt)
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
