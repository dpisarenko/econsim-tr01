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
import org.apache.commons.lang3.StringUtils
import java.util.*

/**
 * Created by pisarenko on 14.05.2016.
 */
object NonBlankStringValidator : IPropertiesFileValueValidator {
    override fun validate(data: Properties, param: String): ValidationResult =
        when {
            StringUtils.isBlank(data.getProperty(param).trim()) ->
                createIncorrectValidationResult("Value of parameter '$param' is blank")
            else ->
                createCorrectValidationResult()
        }
}