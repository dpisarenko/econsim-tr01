package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime

/**
 * Created by pisarenko on 14.04.2016.
 */
class Sim2Accountant(logTarget: StringBuilder,
                     agents: List<IAgent>,
                     resources: List<PlResource>) : AbstractAccountant(logTarget, agents, resources) {
    val fire: (DateTime) -> Boolean = dailyAtMidnight()
    var firstTime:Boolean = true

    override fun measure(time: DateTime) {
        if (fire(time)) {
            if (firstTime) {
                writeResourceData()
                firstTime = false
            }
            logMeasurementTime(time)
            logStockLevels(time.secondsSinceT0())
        }

    }

}
