package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*

/**
 * Created by pisarenko on 08.04.2016.
 */
class Sim1(val logTarget:StringBuilder,
            val flows:MutableList<ResourceFlow>,
            val simParametersProvider: Sim1ParametersProvider) : DefaultSimulation(Timing()) {
    override fun continueCondition(tick:Long): Boolean {
        val t = tick.secondsToSimulationDateTime()
        return (t.monthOfYear <= 3)
    }

    override fun createAgents(): List<IAgent> {
        // TODO: Continue here
        return emptyList()
    }

    override fun createSensors(): List<ISensor> = emptyList()
}