/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
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
            MutableMap<String, List<IPropertiesFileValueValidator>> {
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
