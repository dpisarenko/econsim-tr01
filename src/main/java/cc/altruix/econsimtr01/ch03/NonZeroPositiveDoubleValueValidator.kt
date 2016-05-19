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