package cc.altruix.econsimtr01

import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test

/**
 * Created by pisarenko on 09.04.2016.
 */
class UtilsTests {
    @Test
    fun toSimulationDateTimeSunnyDay() {
        Assertions
            .assertThat(0L.millisToSimulationDateTime())
            .isEqualTo(DateTime(0, 1, 1, 0, 0, 0, 0))
        val t0 = 0L.millisToSimulationDateTime()
        createDate(
                t0,
                0,
                18,
                0
        ).isEqualTo(DateTime(0, 1, 1, 18, 0, 0, 0))
    }
    @Test
    fun randomEventWithProbabilityDefaultScenario() {
        var heads = 0
        for (i in 1..100) {
            if (randomEventWithProbability(0.6)) {
                heads++
            }
        }
        Assertions.assertThat(heads).isEqualTo(60)
    }
}
