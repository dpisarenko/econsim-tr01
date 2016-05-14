package cc.altruix.econsimtr01.ch03

import java.io.File

/**
 * Created by pisarenko on 14.05.2016.
 */
class AgriculturalSimParametersProvider(file: File) : PropertiesFileSimParametersProvider(file) {
    override fun init(file: File): ValidationResult {
        // Returns true, if everything is OK
        // TODO: Implement this
        // TODO: Test this
        return ValidationResult(false, "")
    }
}