package cc.altruix.econsimtr01.flourprod

import cc.altruix.econsimtr01.PlResource
import cc.altruix.econsimtr01.ch03.*
import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class FlourProductionSimulationParametersProvider(file: File) :
    PropertiesFileSimParametersProvider(file) {
    companion object {
        val RESOURCE_FLOUR = PlResource(
            id = "R5",
            name = "Flour",
            unit = "Kilograms"
        )
    }

    override fun initAndValidate() {
        super.initAndValidate()
        if (validity.valid) {
            agents.add(Farmers(this))
            agents.add(Field(this))
            val shack = Shack()
            agents.add(shack)
            agents.forEach { it.init() }
            shack.put(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
                data["InitialSeedQuantity"].toString().toDouble())
            // TODO: Add here the agent mill
        }

    }

    override fun createValidators():
        Map<String, List<IPropertiesFileValueValidator>> {


        // TODO: Implement this
        throw UnsupportedOperationException()
    }
    // TODO: Implement this
}
