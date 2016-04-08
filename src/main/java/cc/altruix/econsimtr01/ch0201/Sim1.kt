package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*

/**
 * Created by pisarenko on 08.04.2016.
 */
class Sim1(val logTarget:StringBuilder,
            val flows:MutableList<ResourceFlow>,
            val simParametersProvider: SimParametersProvider) : DefaultSimulation(Timing()) {
    override fun continueCondition(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun createAgents(): List<IAgent> {
        throw UnsupportedOperationException()
    }

    override fun createSensors(): List<ISensor> {
        throw UnsupportedOperationException()
    }
}