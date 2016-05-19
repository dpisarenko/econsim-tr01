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
 * Created by pisarenko on 08.04.2016.
 */
class Sim1(val logTarget:StringBuilder,
            val flows:MutableList<ResourceFlow>,
            simParametersProvider: Sim1ParametersProvider) :
        DefaultSimulation(simParametersProvider) {
    override fun continueCondition(time: DateTime): Boolean = (time.monthOfYear <= 3)

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
