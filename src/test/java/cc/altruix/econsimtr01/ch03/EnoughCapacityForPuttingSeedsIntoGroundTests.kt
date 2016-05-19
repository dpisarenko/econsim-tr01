/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.DayAndMonth
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 16.05.2016.
 */
class EnoughCapacityForPuttingSeedsIntoGroundTests {
    @Test
    fun validateWiring() {
        validateWiringTestLogic(
                totalRequiredEffort = 1.0,
                availableWorkingTime = 2.0,
                expectedValidity = true,
                expectedMessage = ""
        )
        validateWiringTestLogic(
                totalRequiredEffort = 2.0,
                availableWorkingTime = 1.0,
                expectedValidity = false,
                expectedMessage = "Process 1: It requires 2.0 hours of effort, but only 1.0 is available"
        )
    }
    @Test
    fun calculateTotalRequiredEffort() {
        // Prepare
        val data = Properties()
        data["SizeOfField"] = "250000"
        data["Process1EffortInSquareMeters"] = "0.44"
        val scenario =
                PropertiesFileSimParametersProviderWithPredefinedData(data)
        scenario.initAndValidate()
        val out = EnoughCapacityForPuttingSeedsIntoGround()
        // Run method under test
        val res = out.calculateTotalRequiredEffort(scenario)
        // Verify
        Assertions.assertThat(res).isEqualTo(250000 * 0.44)
    }
    @Test
    fun calculateBusinessDaysBetweenDates() {
        calculateBusinessDaysBetweenDatesTestLogic(
                DayAndMonth(16, 5),
                DayAndMonth(22, 5),
                5
        )
        calculateBusinessDaysBetweenDatesTestLogic(
                DayAndMonth(30, 8),
                DayAndMonth(30, 10),
                44
        )
    }
    @Test
    fun calculateAvailableWorkingTime() {
        // Prepare
        val data = Properties()
        data["NumberOfWorkers"] = "1"
        data["Process1Start"] = "30.08"
        data["Process1End"] = "30.10"
        data["LaborPerBusinessDay"] = "8"
        val scenario = Mockito.spy(
                PropertiesFileSimParametersProviderWithPredefinedData(data)
        )
        scenario.initAndValidate()
        val out = EnoughCapacityForPuttingSeedsIntoGround()
        // Run method under test
        val res = out.calculateAvailableWorkingTime(scenario)
        // Verify
        Assertions.assertThat(res).isEqualTo(1.0 * 44 * 8)

    }
    private fun calculateBusinessDaysBetweenDatesTestLogic(
            start: DayAndMonth,
            end: DayAndMonth,
            expectedDays: Int) {
        // Prepare
        val out = EnoughCapacityForPuttingSeedsIntoGround()
        // Run method under test
        val actDays = out.calculateBusinessDaysBetweenDates(start, end)
        // Verify
        Assertions.assertThat(actDays).isEqualTo(expectedDays)
    }

    private fun validateWiringTestLogic(totalRequiredEffort:Double,
                                        availableWorkingTime:Double,
                                        expectedValidity:Boolean,
                                        expectedMessage:String) {
        // Prepare
        val out = Mockito.spy(EnoughCapacityForPuttingSeedsIntoGround())
        val scenario =
                PropertiesFileSimParametersProviderForTesting(File("someFile"))
        Mockito.doReturn(totalRequiredEffort).`when`(out)
                .calculateTotalRequiredEffort(scenario)
        Mockito.doReturn(availableWorkingTime).`when`(out)
                .calculateAvailableWorkingTime(scenario)
        // Run method under test
        val res = out.validate(scenario)
        // Verify
        Assertions.assertThat(res.valid).isEqualTo(expectedValidity)
        Assertions.assertThat(res.message).isEqualTo(expectedMessage)
    }
}