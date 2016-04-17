package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.PlResource
import org.junit.Test

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Sim2AccountantTests {
    @Test
    fun logStockLevelsCallsLogCohortData() {
        val logTarget = StringBuilder()
        val agents = emptyList<IAgent>()
        val resources = emptyList<PlResource>()
        val out = Sim2Accountant(logTarget,
                agents,
                resources)
        // Run method under test

        // Verify
    }
}
