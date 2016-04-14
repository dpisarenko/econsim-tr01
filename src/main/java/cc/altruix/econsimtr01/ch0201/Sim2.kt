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
        return (t.monthOfYear <= 3)
    }

    override fun createAgents(): List<IAgent> {
        throw UnsupportedOperationException()
    }

    override fun createSensors(): List<ISensor> {
        throw UnsupportedOperationException()
    }
}