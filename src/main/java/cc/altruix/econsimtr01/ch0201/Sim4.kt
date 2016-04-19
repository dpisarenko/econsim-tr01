package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime

/**
 * Created by pisarenko on 19.04.2016.
 */
class Sim4(logTarget:StringBuilder,
           val flows:MutableList<ResourceFlow>,
           simParametersProvider: Sim4ParametersProvider) :
    DefaultSimulation(simParametersProvider){
    // TODO: Test this
    override fun continueCondition(time: DateTime): Boolean = (time.year == 0)

    override fun createAgents(): List<IAgent> {
        // TODO: Test this

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

    private fun attachTransformationsToAgents(transformations: MutableList<PlTransformation>,
                                              agents: MutableList<IAgent>) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createSensors(): List<ISensor> {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }
}