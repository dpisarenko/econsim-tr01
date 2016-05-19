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

import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * Created by pisarenko on 16.05.2016.
 */
class EnoughCapacityForHarvestingTests {
    @Test
    fun validateWiring() {
        validateWiringTestLogic(
                requiredEffort = 1.0,
                availableTime = 2.0,
                expectedValidity = true,
                expectedMessage = ""
        )
        validateWiringTestLogic(
                requiredEffort = 2.0,
                availableTime = 1.0,
                expectedValidity = false,
                expectedMessage = "Process 3: It requires 2.0 hours of " +
                        "effort, but only 1.0 is available"
        )
    }
    @Test
    fun calculateRequiredEffort() {
        // Prepare
        val data = Properties()
        data["SizeOfField"] = "250000"
        data["Process3EffortPerSquareMeter"] = "0.45"
        val scenario =
                PropertiesFileSimParametersProviderWithPredefinedData(data)
        scenario.initAndValidate()
        val out = EnoughCapacityForHarvesting()
        // Run method under test
        val res = out.calculateRequiredEffort(scenario)
        // Verify
        Assertions.assertThat(res).isEqualTo(250000.0 * 0.45)
    }
    @Test
    fun calculateAvailableTime() {
        // Prepare
        val data = Properties()
        data["Process2End"] = "05.07"
        data["Process3End"] = "10.08"
        data["NumberOfWorkers"] = "1"
        data["LaborPerBusinessDay"] = "8"
        val scenario =
                PropertiesFileSimParametersProviderWithPredefinedData(data)
        scenario.initAndValidate()
        val out = EnoughCapacityForHarvesting()
        // Run method under test
        val res = out.calculateAvailableTime(scenario)
        // Verify
        Assertions.assertThat(res).isEqualTo(27.0 * 1 * 8)

    }
    private fun validateWiringTestLogic(requiredEffort: Double,
                                        availableTime: Double,
                                        expectedValidity: Boolean,
                                        expectedMessage: String) {
        // Prepare
        val data = Properties()
        val scenario =
                PropertiesFileSimParametersProviderWithPredefinedData(data)
        val out = Mockito.spy(EnoughCapacityForHarvesting())
        Mockito.doReturn(requiredEffort).`when`(out)
                .calculateRequiredEffort(scenario)
        Mockito.doReturn(availableTime).`when`(out)
                .calculateAvailableTime(scenario)
        // Run method under test
        val res = out.validate(scenario)
        // Verify
        Assertions.assertThat(res.message).isEqualTo(expectedMessage)
        Assertions.assertThat(res.valid).isEqualTo(expectedValidity)
    }
}