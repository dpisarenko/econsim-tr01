package cc.altruix.econsimtr01.ch03

import java.io.File
import java.util.*

/**
 * Created by pisarenko on 15.05.2016.
 */
open class PropertiesFileSimParametersProviderForTesting(file: File) :
        PropertiesFileSimParametersProvider(file){
    open override fun createValidators(): Map<String, List<IPropertiesFileValueValidator>> =
            emptyMap()
}