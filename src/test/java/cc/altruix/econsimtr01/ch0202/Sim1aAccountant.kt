package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.AbstractAccountant
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.PlResource

/**
 * Created by pisarenko on 04.05.2016.
 */
class Sim1aAccountant(logTarget: StringBuilder,
                      agents: List<IAgent>,
                      resources: List<PlResource>)
: AbstractAccountant(logTarget, agents, resources) {
    override fun logStockLevels(time: Long) {
        super.logStockLevels(time)
    }
}