package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.PlResource
import cc.altruix.econsimtr01.shouldBe
import org.fest.assertions.Assertions
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
        val out = createSim2Accountant()
        Mockito.doNothing().`when`(out).logNormalStockLevels(1L)
        Mockito.doNothing().`when`(out).logCohortData(1L)
        // Run method under test
        out.logStockLevels(1L)
        // Verify
        Mockito.verify(out).logNormalStockLevels(1L)
        Mockito.verify(out).logCohortData(1L)
    }

    protected fun createSim2Accountant(): Sim2Accountant {
        val logTarget = StringBuilder()
        val agents = emptyList<IAgent>()
        val resources = emptyList<PlResource>()
        val out = Mockito.spy(Sim2Accountant(logTarget,
                agents,
                resources))
        return out
    }

    @Test
    fun createSubscribersCountByNumberOfInteractions() {
        val out = createSim2Accountant()
        val act = out.createSubscribersCountByNumberOfInteractions()
        act.size.shouldBe(7)
        Assertions.assertThat(act.get(0)).isNull()
        Assertions.assertThat(act.get(1)?.get()).isEqualTo(0)
        Assertions.assertThat(act.get(2)?.get()).isEqualTo(0)
        Assertions.assertThat(act.get(3)?.get()).isEqualTo(0)
        Assertions.assertThat(act.get(4)?.get()).isEqualTo(0)
        Assertions.assertThat(act.get(5)?.get()).isEqualTo(0)
        Assertions.assertThat(act.get(6)?.get()).isEqualTo(0)
        Assertions.assertThat(act.get(7)?.get()).isEqualTo(0)
    }
}
