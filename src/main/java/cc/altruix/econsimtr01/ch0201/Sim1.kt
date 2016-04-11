package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.slf4j.LoggerFactory
import java.util.*

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
        val agents = LinkedList<IAgent>()
        simParametersProvider.flows.forEach { flow ->
            attachFlowToAgent(agents, flow)
        }
        return simParametersProvider.agents
    }

    private fun attachFlowToAgent(agents: LinkedList<IAgent>, flow: PlFlow) {
        val agent = simParametersProvider.agents
                .filter { x -> x.id().equals(flow.src) }
                .first()
        if (agent == null) {
            LOGGER.error("Can't find process ${flow.src}")
        } else {
            flow.agents = agents
            flow.flows = flows

            if (agent is DefaultAgent) {
                agent.addAction(flow)
            }
        }
    }

    override fun createSensors(): List<ISensor> = listOf(Sim1Accountant(logTarget))
}