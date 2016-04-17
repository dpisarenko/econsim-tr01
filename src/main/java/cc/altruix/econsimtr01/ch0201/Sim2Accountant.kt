package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.AbstractAccountant
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.PlResource

/**
 * Created by pisarenko on 14.04.2016.
 */
class Sim2Accountant(logTarget: StringBuilder,
                     agents: List<IAgent>,
                     resources: List<PlResource>) : AbstractAccountant(logTarget, agents, resources) {
    companion object {
        val cohortResources = arrayOf(
                "r06-pc1",
                "r07-pc2",
                "r08-pc2",
                "r09-pc2",
                "r10-pc2",
                "r11-pc2",
                "r12-pc2")
    }

    override fun logStockLevels(time: Long) {
        // TODO: Test this
        logNormalStockLevels(time)
        logCohortData(time)
    }

    internal fun logNormalStockLevels(time: Long) {
        agents.forEach { agent ->
            resources
                    .filter { !cohortResources.contains(it.id) }
                    .forEach { appendResourceAmount(time, agent, it) }
        }
    }

    internal fun logCohortData(time: Long) {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
