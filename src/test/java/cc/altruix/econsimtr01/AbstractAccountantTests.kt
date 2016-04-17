package cc.altruix.econsimtr01

import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito

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
    fun writeResourceDataCallsConvertToPrologString() {
        val logTarget = StringBuilder()
        val agents = emptyList<IAgent>()
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
                                listOf(resource)
                        )
                )
        Mockito.doReturn("foo").`when`(out).convertToPrologString(txt)
        // Run method under test
        out.writeResourceData()
        // Verify
        Mockito.verify(out).convertToPrologString(txt)
        logTarget.toString().shouldBe("resource(r11-pc2, \"foo\", \"People\").${System.lineSeparator()}")
    }
    @Test
    fun convertToPrologStringEscapesApostrophes() {
        val txt =
                "People, who were exposed to Stacy's writings 6 times"
        val out = TestAccountant(
                StringBuilder(),
                emptyList<IAgent>(),
                emptyList<PlResource>()
        )
        // Run method under test
        val act = out.convertToPrologString(txt)
        // Verify
        act.shouldBe("People, who were exposed to Stacy''s writings 6 times")
    }
}