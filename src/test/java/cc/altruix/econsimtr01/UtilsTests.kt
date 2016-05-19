/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
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
