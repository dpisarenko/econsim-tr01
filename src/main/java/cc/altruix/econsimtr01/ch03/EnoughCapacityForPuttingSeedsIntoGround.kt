package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.*
import org.joda.time.DateTimeConstants

/**
 * This validator verifies that the workers will have enough time to put the plants
 * into the soil.
 */
open class EnoughCapacityForPuttingSeedsIntoGround : ISemanticSimulationParametersValidator {
     override fun validate(scenario: PropertiesFileSimParametersProvider): ValidationResult {
        val totalRequiredEffort = calculateTotalRequiredEffort(scenario)
        val availableWorkingTime = calculateAvailableWorkingTime(scenario)
        if (totalRequiredEffort > availableWorkingTime) {
            return createIncorrectValidationResult(
                    "Process 1: It requires $totalRequiredEffort hours of effort, but only $availableWorkingTime is available"
            )
        }
        return createCorrectValidationResult()
    }

    open internal fun calculateAvailableWorkingTime(
            scenario: PropertiesFileSimParametersProvider): Double {
        val numberOfWorkers =
                scenario.data["NumberOfWorkers"].toString().toDouble()
        val processStart =
                scenario.data["Process1Start"].toString().parseDayMonthString()
        val processEnd =
                scenario.data["Process1End"].toString().parseDayMonthString()
        val businessDays =
                calculateBusinessDaysBetweenDates(processStart, processEnd)
        val workingTimePerBusinessDay =
                scenario.data["LaborPerBusinessDay"].toString().toDouble()
        val availableWorkingTime =
                businessDays.toDouble() *
                        workingTimePerBusinessDay *
                        numberOfWorkers
        return availableWorkingTime
    }

    open internal fun calculateTotalRequiredEffort(
            scenario: PropertiesFileSimParametersProvider): Double {
        val sizeOfFieldInSquareMeters =
                scenario.data["SizeOfField"].toString().toDouble()
        val effortPerSquareMetersInHours =
                scenario.data["Process1EffortInSquareMeters"]
                        .toString().toDouble()
        return sizeOfFieldInSquareMeters * effortPerSquareMetersInHours
    }

    open internal fun calculateBusinessDaysBetweenDates(
            start: DayAndMonth,
            end: DayAndMonth
    ): Int {
        var day = start.toDateTime()
        val end = end.toDateTime()
        var businessDays = 0
        do {
            if ((day.dayOfWeek != DateTimeConstants.SATURDAY) && (day
                    .dayOfWeek != DateTimeConstants.SUNDAY)) {
                businessDays++
            }
            day = day.plusDays(1)
        } while (day.isBefore(end) || day.isEqual(end))
        return businessDays
    }
}