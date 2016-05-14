package cc.altruix.econsimtr01.ch03

import java.util.*

/**
 * Created by pisarenko on 14.05.2016.
 */
object NonBlankStringValidator : IPropertiesFileValueValidator {
    override fun validate(data: Properties, param: String): ValidationResult {
        // TODO: Implement this
        // TODO: Validate this
        return ValidationResult(false, "")
    }
}