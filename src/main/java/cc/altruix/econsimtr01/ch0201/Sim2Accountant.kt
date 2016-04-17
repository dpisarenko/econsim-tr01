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

    override fun logStockLevels(time: Long) {
        super.logStockLevels(time)
    }
}
