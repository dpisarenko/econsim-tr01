package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.millisToSimulationDateTime
import cc.altruix.econsimtr01.secondsToSimulationDateTime
import cc.altruix.econsimtr01.shouldBe
import org.junit.Test

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
}