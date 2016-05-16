/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.createCorrectValidationResult
import cc.altruix.econsimtr01.createIncorrectValidationResult
import cc.altruix.econsimtr01.parseDayMonthString
import cc.altruix.econsimtr01.toDateTime

/**
 * Created by pisarenko on 16.05.2016.
 */
class OneDateBeforeOtherValidator(val earlierDateParam:String,
                                  val laterDateParam:String) :
        ISemanticSimulationParametersValidator {
    override fun validate(scenario: PropertiesFileSimParametersProvider):
            ValidationResult {
        val start = extractDateTime(scenario.data[earlierDateParam])
        val end = extractDateTime(scenario.data[laterDateParam])
        if (start.isBefore(end)) {
            return createCorrectValidationResult()
        }
        return createIncorrectValidationResult("$earlierDateParam " +
                "('${scenario.data[earlierDateParam]}') must be before " +
                "$laterDateParam ('${scenario.data[laterDateParam]}')")
    }

    internal fun extractDateTime(txtValue: Any?) =
            txtValue.toString().parseDayMonthString().toDateTime()
}