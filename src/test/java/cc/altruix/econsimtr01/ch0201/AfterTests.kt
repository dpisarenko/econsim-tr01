package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
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
        out.nextFireTime.shouldBe(-1)
    }

    @Test
    fun updateNextFiringTimeSunnyDay() {
        val out = After("f1")
        val t = 0L.millisToSimulationDateTime()
        out.updateNextFiringTime(t)
        out.nextFireTime.shouldBe(t.millis + 1L)
    }

    @Test
    fun isMidnightSunnyDay() {
        val out = After("f1")
        val t0 = 0L.millisToSimulationDateTime()
        out.isMidnight(t0).shouldBeTrue()
        out.isMidnight(t0.plusSeconds(1)).shouldBeFalse()
    }

    @Test
    @Ignore
    fun invokeResetsNextFireTimeAtMidnight() {
        val out = Mockito.spy(After("f1"))
        out.nextFireTime = 1



    }
}