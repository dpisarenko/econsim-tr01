package cc.altruix.econsimtr01.ch03

import java.io.File

/**
 * Created by pisarenko on 14.05.2016.
 */
open class PropertiesFileSimParametersProviderForTesting(file: File,
                                                    val valRes:ValidationResult) :
        PropertiesFileSimParametersProvider(file) {
    override fun createValidators(): Map<String, List<IPropertiesFileValueValidator>> = emptyMap()
}