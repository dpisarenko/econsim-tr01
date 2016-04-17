package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.shouldBe
import cc.altruix.econsimtr01.toPrologTheory
import org.junit.Test

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class SubscribersCohortColFunctionTests {
    @Test
    fun invoke() {
        val prolog = """
        resourceLevel(86400, 'list', 'r06-pc1', 143).
        resourceLevel(86400, 'list', 'r07-pc2', 144).
        resourceLevel(86400, 'list', 'r08-pc3', 145).
        resourceLevel(86400, 'list', 'r09-pc4', 146).
        resourceLevel(86400, 'list', 'r10-pc5', 147).
        resourceLevel(86400, 'list', 'r11-pc6', 148).
        resourceLevel(86400, 'list', 'r12-pc7', 149).
        """.toPrologTheory()

        testLogic(prolog, 1, "143.0")
        testLogic(prolog, 2, "144.0")
        testLogic(prolog, 3, "145.0")
        testLogic(prolog, 4, "146.0")
        testLogic(prolog, 5, "147.0")
        testLogic(prolog, 6, "148.0")
        testLogic(prolog, 7, "149.0")
    }

    private fun testLogic(prolog: Prolog,
                          interactions: Int,
                          expectedResult: String) {
        SubscribersCohortColFunction(interactions).invoke(prolog, 86400L).shouldBe(expectedResult)
    }
}
