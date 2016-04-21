package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.shouldBeFalse
import cc.altruix.econsimtr01.shouldBeTrue
import cc.altruix.econsimtr01.toPrologTheory
import org.junit.Ignore
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
        out.softwareComplete("resourceLevel(86400, 'stacy', 'r14', 479.0).".toPrologTheory(),
                86400L).shouldBeFalse()
        out.softwareComplete("resourceLevel(86400, 'stacy', 'r14', 480.0).".toPrologTheory(),
                86400L).shouldBeTrue()
        out.softwareComplete("resourceLevel(86400, 'stacy', 'r14', 481.0).".toPrologTheory(),
                86400L).shouldBeTrue()

    }
    @Test
    @Ignore
    fun enoughPeopleInList() {
        // TODO: Finish this test
        val out = TransitionReadinessColFunction()
        out.enoughPeopleInList("resourceLevel(86400, list, 'r06-pc1', 0.0).".toPrologTheory(), 86400L).shouldBeFalse()
        out.enoughPeopleInList("resourceLevel(86400, list, 'r06-pc1', 999.0).".toPrologTheory(), 86400L).shouldBeFalse()
        out.enoughPeopleInList("resourceLevel(86400, list, 'r06-pc1', 1000.0).".toPrologTheory(), 86400L).shouldBeTrue()
        out.enoughPeopleInList("resourceLevel(86400, list, 'r06-pc1', 1001.0).".toPrologTheory(), 86400L).shouldBeTrue()
    }
}