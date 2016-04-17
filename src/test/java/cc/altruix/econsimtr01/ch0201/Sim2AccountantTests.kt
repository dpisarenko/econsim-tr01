package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.PlResource
import org.junit.Test
import org.mockito.Mockito

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
        val out = Mockito.spy(Sim2Accountant(logTarget,
                agents,
                resources))
        Mockito.doNothing().`when`(out).logNormalStockLevels(1L)
        Mockito.doNothing().`when`(out).logCohortData(1L)
        // Run method under test
        out.log
        // Verify
    }
}
