/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import java.io.File
import java.util.*

/**
 * Created by pisarenko on 14.05.2016.
 */
class AgriculturalSimParametersProvider(file: File) :
        PropertiesFileSimParametersProvider(file) {
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
            // TODO: Test this
            agents.add(Farmers(this))
            agents.add(Field(this))
        }
    }
}