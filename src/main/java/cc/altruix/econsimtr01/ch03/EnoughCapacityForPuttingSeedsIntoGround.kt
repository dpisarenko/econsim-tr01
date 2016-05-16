package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.DayAndMonth
import cc.altruix.econsimtr01.createCorrectValidationResult
import cc.altruix.econsimtr01.createIncorrectValidationResult
import cc.altruix.econsimtr01.parseDayMonthString

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
        // TODO: Implement this
        // TODO: Test this
        val numberOfWorkers = scenario.data["NumberOfWorkers"].toString().toDouble()
        val processStart = scenario.data["Process1Start"].toString().parseDayMonthString()
        val processEnd = scenario.data["Process1End"].toString().parseDayMonthString()
        val businessDays = calculateBusinessDaysBetweenDates(processStart, processEnd)
        val workingTimePerBusinessDay = scenario.data["LaborPerBusinessDay"].toString().toDouble()
        val availableWorkingTime = businessDays.toDouble() * workingTimePerBusinessDay * numberOfWorkers
        return availableWorkingTime
    }

    open internal fun calculateTotalRequiredEffort(
            scenario: PropertiesFileSimParametersProvider): Double {
        // TODO: Implement this
        // TODO: Test this
        val sizeOfFieldInSquareMeters =
                scenario.data["SizeOfField"].toString().toDouble()
        val effortPerSquareMetersInHours =
                scenario.data["Process1EffortInSquareMeters"]
                        .toString().toDouble()
        return sizeOfFieldInSquareMeters * effortPerSquareMetersInHours
    }

    open internal fun calculateBusinessDaysBetweenDates(
            processStart: DayAndMonth,
            processEnd: DayAndMonth
    ): Int {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}