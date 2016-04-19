package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.DefaultSimulation
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ISensor
import cc.altruix.econsimtr01.ResourceFlow
import org.joda.time.DateTime

/**
 * Created by pisarenko on 19.04.2016.
 */
class Sim4(logTarget:StringBuilder,
           flows:MutableList<ResourceFlow>,
           simParametersProvider: Sim4ParametersProvider) :
    DefaultSimulation(simParametersProvider){
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