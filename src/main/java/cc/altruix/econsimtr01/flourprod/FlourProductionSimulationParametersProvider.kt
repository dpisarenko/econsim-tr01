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
