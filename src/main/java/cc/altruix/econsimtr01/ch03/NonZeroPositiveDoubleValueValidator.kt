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
import java.util.*

/**
 * Created by pisarenko on 14.05.2016.
 */
object NonZeroPositiveDoubleValueValidator : IPropertiesFileValueValidator {
    override fun validate(data: Properties, param:String): ValidationResult {
        try {
            val pval:Double = data[param]?.toString()?.toDouble() ?: 0.0
            when {
                pval == 0.0 ->
                    return createIncorrectValidationResult("Value of '$param' is zero")
                pval < 0.0 ->
                    return createIncorrectValidationResult("Value of '$param' is negative ($pval)")
            }
        } catch (exception:NumberFormatException) {
            return createIncorrectValidationResult(
                    "Value of '$param' is equal to '${data[param]}', which isn't numeric"
            )
        }
        return createCorrectValidationResult()
    }
}