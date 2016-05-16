/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.createCorrectValidationResult
import cc.altruix.econsimtr01.createIncorrectValidationResult

/**
 * Created by pisarenko on 16.05.2016.
 */
class EnoughSeedsAtTheStartValidator : ISemanticSimulationParametersValidator {
    override fun validate(scenario: PropertiesFileSimParametersProvider):
            ValidationResult {
        // TODO: Test this
        val requiredAmount = calculateRequiredAmount(scenario)
        val actualAmount = scenario.data["InitialSeedQuantity"].
                toString().toDouble()
        if (actualAmount < requiredAmount) {
            return createIncorrectValidationResult("We need $requiredAmount " +
                    "kg of seeds, but only have $actualAmount")
        }
        return createCorrectValidationResult()
    }

    private fun calculateRequiredAmount(
            scenario: PropertiesFileSimParametersProvider): Double {
        val fieldSize = scenario.data["SizeOfField"].toString().toDouble()
        val seedsPerSquareMeter = scenario
                .data["Process1QuantityOfSeeds"].toString().toDouble()
        return fieldSize * seedsPerSquareMeter
    }
}