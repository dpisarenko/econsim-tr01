/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.createCorrectValidationResult
import cc.altruix.econsimtr01.createIncorrectValidationResult

/**
 * Created by pisarenko on 15.05.2016.
 */
open class EnoughCapacityForHarvesting :
        ISemanticSimulationParametersValidator {
    override fun validate(scenario: PropertiesFileSimParametersProvider)
            : ValidationResult {
        // TODO: Test this
        val requiredEffort = calculateRequiredEffort(scenario)
        val availableTime = calculateAvailableTime(scenario)
        if (requiredEffort > availableTime) {
            return createIncorrectValidationResult(
                    "Process 3: It requires $requiredEffort hours of effort, but" +
                    " only $availableTime is available")
        }
        return createCorrectValidationResult()
    }

    open internal fun calculateAvailableTime(scenario:
                                             PropertiesFileSimParametersProvider): Double {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    open internal fun calculateRequiredEffort(scenario:
                                  PropertiesFileSimParametersProvider): Double {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}