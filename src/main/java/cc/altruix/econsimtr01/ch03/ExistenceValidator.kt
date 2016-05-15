package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.createCorrectValidationResult
import cc.altruix.econsimtr01.createIncorrectValidationResult
import java.util.*

/**
 * Created by pisarenko on 14.05.2016.
 */
object ExistenceValidator : IPropertiesFileValueValidator {
    override fun validate(data: Properties, param:String): ValidationResult {
        if (data.containsKey(param)) {
            return createCorrectValidationResult()
        }
        return createIncorrectValidationResult("Missing parameter '$param'")
    }
}