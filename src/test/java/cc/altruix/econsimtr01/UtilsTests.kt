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
        randomEventWithProbabilityTestLogic(0.1, 10)
        randomEventWithProbabilityTestLogic(0.2, 20)
        randomEventWithProbabilityTestLogic(0.3, 30)
        randomEventWithProbabilityTestLogic(0.4, 40)
        randomEventWithProbabilityTestLogic(0.5, 50)
        randomEventWithProbabilityTestLogic(0.6, 61)
        randomEventWithProbabilityTestLogic(0.7, 70)
        randomEventWithProbabilityTestLogic(0.8, 80)
        randomEventWithProbabilityTestLogic(0.9, 90)
        randomEventWithProbabilityTestLogic(1.0, 100)
    }

    private fun randomEventWithProbabilityTestLogic(
            probability: Double, expectedNumberOfHeads: Int): Unit {
        var heads = 0
        for (i in 1..100) {
            if (randomEventWithProbability(probability)) {
                heads++
            }
        }
        Assertions.assertThat(heads).isEqualTo(expectedNumberOfHeads)
    }
}
