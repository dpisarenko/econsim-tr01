package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 08.04.2016.
 */
class Sim1(val logTarget:StringBuilder,
            val flows:MutableList<ResourceFlow>,
            val simParametersProvider: Sim1ParametersProvider) : DefaultSimulation(Timing()) {
    val LOGGER = LoggerFactory.getLogger(Sim1::class.java)
    override fun continueCondition(tick:Long): Boolean {
        val t = tick.secondsToSimulationDateTime()
        return (t.monthOfYear <= 3)
    }

    override fun createAgents(): List<IAgent> {
        simParametersProvider.flows.forEach { flow ->
            val agent = simParametersProvider.agents
                    .filter {x -> x.id().equals(flow.src)}
                    .first()
            if (agent == null) {
                LOGGER.error("Can't find process ${flow.src}")
            } else {
                // TODO: Continue here
            }

        }
        return simParametersProvider.agents
    }

    override fun createSensors(): List<ISensor>{

        return emptyList()
    }
}