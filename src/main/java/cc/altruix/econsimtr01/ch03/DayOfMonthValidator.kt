/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.createCorrectValidationResult
import cc.altruix.econsimtr01.createIncorrectValidationResult
import org.apache.commons.lang3.StringUtils
import java.util.*

/**
 * Created by pisarenko on 14.05.2016.
 */
object DayOfMonthValidator : IPropertiesFileValueValidator {
    override fun validate(data: Properties, param:String): ValidationResult {
        val pvalue = data[param].toString()
        if (!pvalue.contains('.')) {
            return createWrongFormatResult(param, pvalue)
        }
        val parts = pvalue.split(".")
        if (parts.size != 2) {
            return createWrongFormatResult(param, pvalue)
        }
        val nonNumeric = parts.filter { !StringUtils.isNumeric(it) }.count() > 0
        if (nonNumeric) {
            return createWrongFormatResult(param, pvalue)
        }
        val day = parts[0].toInt()
        if (day < 1) {
            return createWrongDayResult(param, pvalue)
        }
        val month = parts[1].toInt()
        if ((month < 1) || (month > 12)) {
            return createWrongMonthResult(param, pvalue)
        }
        val maxDaysInMonth = calculateMaxDaysInMonth(month)
        if (day > maxDaysInMonth) {
            return createWrongDayResult(param, pvalue)
        }
        return createCorrectValidationResult()
    }

    private fun calculateMaxDaysInMonth(month: Int): Int = when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        2 -> 29
        4, 6, 9, 11 -> 30
        else -> 0
    }

    private fun createWrongFormatResult(param: String, value: String): ValidationResult
        = createIncorrectValidationResult("Value '$value' of '$param' has invalid format")

    private fun createWrongMonthResult(param: String, pvalue: String): ValidationResult =
            createIncorrectValidationResult("Invalid month in parameter '$param' value ('$pvalue')")

    private fun createWrongDayResult(param: String, pvalue: String): ValidationResult =
            createIncorrectValidationResult("Invalid day in parameter '$param' value ('$pvalue')")
}