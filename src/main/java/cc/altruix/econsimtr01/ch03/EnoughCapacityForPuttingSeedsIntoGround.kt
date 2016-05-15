package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.parseDayMonthString

/**
 * This validator verifies that the workers will have enough time to put the plants
 * into the soil.
 */
object EnoughCapacityForPuttingSeedsIntoGround : ISemanticSimulationParametersValidator {
    override fun validate(scenario: PropertiesFileSimParametersProvider): ValidationResult {
        // TODO: Implement this
        // TODO: Test this

        val sizeOfFieldInSquareMeters = scenario.data["SizeOfField"].toString().toDouble()
        val effortPerSquareMetersInHours = scenario.data["Process1EffortInSquareMeters"].toString().toDouble()
        val totalRequiredEffort = sizeOfFieldInSquareMeters * effortPerSquareMetersInHours


        val laborPerBusinessDayInHours = scenario.data["LaborPerBusinessDay"]
        val numberOfWorkers = scenario.data["NumberOfWorkers"].toString().toDouble()

        val processStart = scenario.data["Process1Start"].toString().parseDayMonthString()
        val startDay = processStart.first
        val startMonth = processStart.second

        // TODO: Continue here

        throw UnsupportedOperationException()
    }
}