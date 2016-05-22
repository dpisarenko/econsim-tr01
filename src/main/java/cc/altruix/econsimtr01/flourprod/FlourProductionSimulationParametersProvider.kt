package cc.altruix.econsimtr01.flourprod

import cc.altruix.econsimtr01.MaxValueValidator
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
            agents.add(Mill(this))
            agents.forEach { it.init() }
            shack.put(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
                data["InitialSeedQuantity"].toString().toDouble())
        }

    }

    override fun createValidators():
        Map<String, List<IPropertiesFileValueValidator>> {
        val aspp = AgriculturalSimParametersProvider(File("someFile"))
        val validators = aspp.createValidators()
        validators["FlourConversionFactor"] =listOf(ExistenceValidator,
            NonBlankStringValidator,
            NonZeroPositiveDoubleValueValidator,
            MaxValueValidator(1.0))
        validators["MillThroughput"] =listOf(ExistenceValidator,
            NonBlankStringValidator,
            NonZeroPositiveDoubleValueValidator)
        validators["MillMaxWorkingTimePerDay"] = listOf(ExistenceValidator,
            NonBlankStringValidator,
            NonZeroPositiveDoubleValueValidator,
            MaxValueValidator(24.0))
        return validators
    }
}
