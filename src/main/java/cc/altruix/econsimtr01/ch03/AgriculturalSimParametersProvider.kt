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

import cc.altruix.econsimtr01.PlResource
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 14.05.2016.
 */
open class AgriculturalSimParametersProvider(file: File) :
        PropertiesFileSimParametersProvider(file) {
    companion object {
        val RESOURCE_AREA_WITH_SEEDS = PlResource(
                id = "R1",
                name = "Area with seeds",
                unit = "Square meters"
        )
        val RESOURCE_AREA_WITH_CROP = PlResource(
                id = "R2",
                name = "Area with crop",
                unit = "Square meters"
        )
        val RESOURCE_EMPTY_AREA = PlResource(
                id = "R3",
                name = "Area with neither seeds, nor crop in it",
                unit = "Square meters"
        )
        val RESOURCE_SEEDS = PlResource(
                id = "R4",
                name = "Seeds",
                unit = "Kilograms"
        )
    }
    override fun createValidators():
            Map<String, List<IPropertiesFileValueValidator>> {
        val validators = HashMap<String, List<IPropertiesFileValueValidator>>()
        validators["SimulationName"] = listOf(
                ExistenceValidator,
                NonBlankStringValidator
        )
        listOf(
                "SizeOfField",
                "NumberOfWorkers",
                "Process1QuantityOfSeeds",
                "Process1EffortInSquareMeters",
                "Process2YieldPerSquareMeter",
                "Process3EffortPerSquareMeter",
                "LaborPerBusinessDay",
                "InitialSeedQuantity"
        ).forEach { param ->
            validators[param] = listOf(
                    ExistenceValidator,
                    NonBlankStringValidator,
                    NonZeroPositiveDoubleValueValidator
            )
        }
        listOf(
                "Process1Start",
                "Process1End",
                "Process2End",
                "Process3End"
        ).forEach { param ->
            validators[param] = listOf(
                    ExistenceValidator,
                    NonBlankStringValidator,
                    DayOfMonthValidator
            )
        }
        return validators
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
        }
    }
}
