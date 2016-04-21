package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.shouldBe
import cc.altruix.econsimtr01.toPrologTheory
import org.junit.Test

/**
 * Created by pisarenko on 21.04.2016.
 */
class PercentageResourceLevelColFunctionTests {
    @Test
    fun invoke() {
        val out = PercentageResourceLevelColFunction("stacy", "r14", 480.0)
        out.invoke("resourceLevel(86400, 'stacy', 'r14', 0.0).".toPrologTheory(),
                86400L).shouldBe("")
        out.invoke("resourceLevel(86400, 'stacy', 'r14', 480.0).".toPrologTheory(),
                86400L).shouldBe("")
    }
}