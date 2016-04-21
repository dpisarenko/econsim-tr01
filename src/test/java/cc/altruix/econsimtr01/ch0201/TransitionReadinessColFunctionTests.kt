package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.shouldBeFalse
import cc.altruix.econsimtr01.toPrologTheory
import org.junit.Test

/**
 * Created by pisarenko on 21.04.2016.
 */
class TransitionReadinessColFunctionTests {
    @Test
    fun softwareComplete() {
        val out = TransitionReadinessColFunction()
        out.softwareComplete("resourceLevel(86400, 'stacy', 'r14', 0.0).".toPrologTheory(),
                86400L).shouldBeFalse()
        out.softwareComplete("resourceLevel(86400, 'stacy', 'r14', 480.0).".toPrologTheory(),
                86400L).shouldBeFalse()

    }
}