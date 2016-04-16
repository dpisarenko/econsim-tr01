package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.millisToSimulationDateTime
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class F2FlowTests {
    @Test
    fun runSunnyDay() {
        val out = createObjectUnderTest()
        out.list = ListAgent("list")
        out.agents = emptyList()
        val t = 0L.millisToSimulationDateTime()
        val priceOfSoftwareSoldToNewlyActivatedAudience = 10.35
        Mockito.doReturn(priceOfSoftwareSoldToNewlyActivatedAudience)
            .`when`(out).calculatePriceOfSoftwareSold()
        // Run method under test
        out.run(t)
        // Verify
        Mockito.verify(out).run(priceOfSoftwareSoldToNewlyActivatedAudience, t)
    }

    @Test
    fun calculatePriceOfSoftwareSoldSunnyDay() {
        val out = createObjectUnderTest()
        val list = Mockito.spy(ListAgent("list"))
        list.buyersCount = 321
        out.list = list
        // Run method under test
        val act = out.calculatePriceOfSoftwareSold()
        // Verify
        Assertions.assertThat(act).isEqualTo(321 * 23.45)
    }

    private fun createObjectUnderTest(): F2Flow {
        return Mockito.spy(F2Flow("id",
                "src", "target", "res",
                null, { true }, 23.45))
    }
}
