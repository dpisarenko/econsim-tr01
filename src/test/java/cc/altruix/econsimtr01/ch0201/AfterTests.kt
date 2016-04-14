package cc.altruix.econsimtr01.ch0201

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
}