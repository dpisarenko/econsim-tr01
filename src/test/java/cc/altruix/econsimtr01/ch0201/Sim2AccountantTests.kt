package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.PlResource
import cc.altruix.econsimtr01.shouldBe
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

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
    @Test
    fun logCohortData() {
        val out = createSim2Accountant()
        val list = ListAgent("list")
        Mockito.doReturn(list).`when`(out).findList()
        val subscribersCountByNumberOfInteractions =
                HashMap<Int, AtomicInteger>()
        Mockito.doReturn(subscribersCountByNumberOfInteractions)
            .`when`(out).calculateSubscribersCountByNumberOfInteractions(list)
        val time = 1L
        Mockito.doNothing().`when`(out).logSubscribersCountByNumberOfInteractions(list,
                subscribersCountByNumberOfInteractions,
                time, Mockito.doNothing().`when`(out).logTarget)
        // Run method under test
        out.logCohortData(time)
        // Verify
        Mockito.verify(out).findList()
        Mockito.verify(out).calculateSubscribersCountByNumberOfInteractions(list)
        Mockito.verify(out).logSubscribersCountByNumberOfInteractions(list,
                subscribersCountByNumberOfInteractions,
                time, Mockito.verify(out).logTarget)

    }
    @Test
    fun logSubscribersCountByNumberOfInteractions() {
        val out = createSim2Accountant()
        val subscribersCountByNumberOfInteractions =
                hashMapOf(
                        Pair(1, AtomicInteger(10)),
                        Pair(2, AtomicInteger(20)),
                        Pair(3, AtomicInteger(30)),
                        Pair(4, AtomicInteger(40)),
                        Pair(5, AtomicInteger(50)),
                        Pair(6, AtomicInteger(60)),
                        Pair(7, AtomicInteger(70))
                )
        val log = StringBuilder()
        val list = ListAgent("list")
        // Run method under test
        out.logSubscribersCountByNumberOfInteractions(list,
                subscribersCountByNumberOfInteractions,
                1L,
                log)
        // Verify
        var nl = System.lineSeparator()
        log.toString().shouldBe("""resourceLevel(1, 'list', 'r06-pc1', 10).
resourceLevel(1, 'list', 'r07-pc2', 20).
resourceLevel(1, 'list', 'r08-pc3', 30).
resourceLevel(1, 'list', 'r09-pc4', 40).
resourceLevel(1, 'list', 'r10-pc5', 50).
resourceLevel(1, 'list', 'r11-pc6', 60).
resourceLevel(1, 'list', 'r12-pc7', 70).
""".replace("\n", nl))
    }
}
