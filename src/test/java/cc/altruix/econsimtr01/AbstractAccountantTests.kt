package cc.altruix.econsimtr01

import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class AbstractAccountantTests {
    open class TestAccountant(logTarget:StringBuilder,
                         agents:List<IAgent>,
                         resources:List<PlResource>)
        : AbstractAccountant(logTarget, agents, resources) {
        override fun measure(time: DateTime) {
        }
    }

    @Test
    fun writeResourceDataEscapesApostrophes() {
        val logTarget = StringBuilder()
        val agents = emptyList<IAgent>()
        val resources = ArrayList<PlResource>()
        // resource("r11-pc2", "People, who were exposed to Stacy''s writings 6 times", "People").
        val txt =
                "People, who were exposed to Stacy's writings 6 times"
        val resource = PlResource(
                "r11-pc2",
                txt,
                "People")
        val out = Mockito.spy(
                        TestAccountant(
                                logTarget,
                                agents,
                                resources
                        )
                )
        Mockito.doReturn("foo").`when`(out).convertToPrologString(txt)
        // Run method under test
        out.writeResourceData()
        // Verify
        Mockito.verify(out).convertToPrologString(txt)
        logTarget.toString().shouldBe("foo${System.lineSeparator()}")
    }
}
