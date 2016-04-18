package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by pisarenko on 14.04.2016.
 */
class AfterTests {
    @Test
    fun initResetsNextFireTime() {
        val out = After("f1")
        Assertions.assertThat(out.nextFireTime).isNull()
    }

    @Test
    fun updateNextFiringTimeSunnyDay() {
        val out = After("f1")
        val t = 0L.millisToSimulationDateTime()
        out.updateNextFiringTime(t)
        Assertions.assertThat(out.nextFireTime).isEqualTo(t.plusMinutes(1))
    }

    @Test
    fun isMidnightSunnyDay() {
        val out = After("f1")
        val t0 = 0L.millisToSimulationDateTime()
        out.isMidnight(t0).shouldBeTrue()
        out.isMidnight(t0.plusSeconds(1)).shouldBeFalse()
    }

    @Test
    fun invokeResetsNextFireTimeWhenItFires() {
        val out = Mockito.spy(After("f1"))
        val t0 = 0L.millisToSimulationDateTime()
        out.nextFireTime = t0.plusMinutes(1)
        out.invoke(t0).shouldBeFalse()
        Assertions.assertThat(out.nextFireTime).isNotNull

        out.invoke(t0.plusMinutes(1)).shouldBeTrue()
        Assertions.assertThat(out.nextFireTime).isNull()
    }
    @Test
    fun connectToInitiatingFunctionFlowSunnyDay() {
        val flow1 = createFlow("f1")
        val flow2 = createFlow("f2")
        val out = After("f1")
        // Run method under test
        out.connectToInitiatingFunctionFlow(mutableListOf(flow1, flow2))
        // Verify
        Mockito.verify(flow1).addFollowUpFlow(out)
        Mockito.verify(flow2, Mockito.never()).addFollowUpFlow(out)
    }

    private fun createFlow(id: String): PlFlow {
        val flow1 = Mockito.spy(
                PlFlow(
                        id,
                        "src",
                        "target",
                        "resource",
                        null,
                        { x -> true }
                )
        )
        return flow1
    }
}