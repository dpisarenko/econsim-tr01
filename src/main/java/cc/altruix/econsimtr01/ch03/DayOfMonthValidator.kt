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