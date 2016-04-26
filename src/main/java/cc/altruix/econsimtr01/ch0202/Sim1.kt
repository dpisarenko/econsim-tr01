package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.DefaultSimulation
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ISensor
import cc.altruix.econsimtr01.ResourceFlow
import org.joda.time.DateTime

/**
 * Created by pisarenko on 26.04.2016.
 */
class Sim1(val logTarget:StringBuilder,
           val flows:MutableList<ResourceFlow>,
           simParametersProvider: Sim1ParametersProvider) : DefaultSimulation(simParametersProvider) {
    override fun continueCondition(tick: DateTime): Boolean {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }

    override fun createAgents(): List<IAgent> {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }

    override fun createSensors(): List<ISensor> {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }
}