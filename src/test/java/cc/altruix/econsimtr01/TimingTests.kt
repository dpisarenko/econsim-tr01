package cc.altruix.econsimtr01

import org.fest.assertions.Assertions
import org.junit.Test

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class TimingTests {
    @Test
    fun sunnyDay() {
        val out = Timing()
        runForOneHour(out)
        Assertions.assertThat(out.now().standardHours).isEqualTo(1)
        runForOneHour(out)
        Assertions.assertThat(out.now().standardHours).isEqualTo(2)
    }

    private fun runForOneHour(out: Timing) {
        for (minute in 1..60) {
            out.tick()
        }
    }
}
