package cc.altruix.econsimtr01.flourprod

import cc.altruix.econsimtr01.ch03.IPropertiesFileValueValidator
import cc.altruix.econsimtr01.ch03.PropertiesFileSimParametersProvider
import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class FlourProductionSimulationParametersProvider(file: File) :
    PropertiesFileSimParametersProvider(file) {
    override fun createValidators():
        Map<String, List<IPropertiesFileValueValidator>> {
        // TODO: Implement this
        throw UnsupportedOperationException()
    }
    // TODO: Implement this
}
