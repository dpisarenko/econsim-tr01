/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

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
        randomEventWithProbabilityTestLogic(0.1, 9)
        randomEventWithProbabilityTestLogic(0.2, 16)
        randomEventWithProbabilityTestLogic(0.3, 36)
        randomEventWithProbabilityTestLogic(0.4, 38)
        randomEventWithProbabilityTestLogic(0.5, 50)
        randomEventWithProbabilityTestLogic(0.6, 70)
        randomEventWithProbabilityTestLogic(0.7, 68)
        randomEventWithProbabilityTestLogic(0.8, 82)
        randomEventWithProbabilityTestLogic(0.9, 93)
        randomEventWithProbabilityTestLogic(1.0, 100)
    }
    @Test
    fun dateTimeBetween() {
        dateTimeBetweenTestLogic(
                start = DayAndMonth(30, 8),
                end = DayAndMonth(30, 10),
                time = DateTime(2016, 8, 29, 0, 0),
                expectedResult = false
        )
        dateTimeBetweenTestLogic(
                start = DayAndMonth(30, 8),
                end = DayAndMonth(30, 10),
                time = DateTime(2016, 8, 30, 0, 0),
                expectedResult = true
        )
        dateTimeBetweenTestLogic(
                start = DayAndMonth(30, 8),
                end = DayAndMonth(30, 10),
                time = DateTime(2016, 9, 1, 0, 0),
                expectedResult = true
        )
        dateTimeBetweenTestLogic(
                start = DayAndMonth(30, 8),
                end = DayAndMonth(30, 10),
                time = DateTime(2016, 8, 31, 0, 0),
                expectedResult = true
        )
        dateTimeBetweenTestLogic(
                start = DayAndMonth(30, 8),
                end = DayAndMonth(30, 10),
                time = DateTime(2016, 10, 30, 0, 0),
                expectedResult = true
        )
        dateTimeBetweenTestLogic(
                start = DayAndMonth(30, 8),
                end = DayAndMonth(30, 10),
                time = DateTime(2016, 10, 31, 0, 0),
                expectedResult = false
        )
    }

    private fun dateTimeBetweenTestLogic(start: DayAndMonth,
                                         end: DayAndMonth,
                                         time: DateTime,
                                         expectedResult: Boolean) =
            Assertions.assertThat(time.between(start, end))
                    .isEqualTo(expectedResult)

    private fun randomEventWithProbabilityTestLogic(
            probability: Double, expectedNumberOfHeads: Int) {
        var heads = 0
        for (i in 1..100) {
            if (randomEventWithProbability(probability)) {
                heads++
            }
        }
        Assertions.assertThat(heads).isEqualTo(expectedNumberOfHeads)
    }
}
