package cc.altruix.econsimtr01

import org.fest.assertions.Assertions
import org.junit.Test
import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Simulation1Tests {
    @Test
    fun test() {
        val log = StringBuilder()
        val sim = Simulation1(log)

        // Run method under test
        sim.run()

        // Verify
        val expectedResult = File("src/test/resources/Simulation1Tests.test.expected.txt").readText()
        Assertions.assertThat(log.toString()).isEqualTo(expectedResult)
    }
}
