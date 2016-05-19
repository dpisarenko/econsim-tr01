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

import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.millisToSimulationDateTime
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.io.File

/**
 * Created by pisarenko on 17.05.2016.
 */
class Process3Tests {
    @Test
    fun timeToRunWiring() {
        runWiringTestLogic(
                timeBetweenStartAndEnd = false,
                evenHourAndMinute = false,
                cropToCollectAvailable = false,
                businessDay = false,
                expectedResult = false
        )
        runWiringTestLogic(
                timeBetweenStartAndEnd = true,
                evenHourAndMinute = false,
                cropToCollectAvailable = false,
                businessDay = false,
                expectedResult = false
        )
        runWiringTestLogic(
                timeBetweenStartAndEnd = false,
                evenHourAndMinute = true,
                cropToCollectAvailable = false,
                businessDay = false,
                expectedResult = false
        )
        runWiringTestLogic(
                timeBetweenStartAndEnd = false,
                evenHourAndMinute = false,
                cropToCollectAvailable = true,
                businessDay = false,
                expectedResult = false
        )
        runWiringTestLogic(
                timeBetweenStartAndEnd = true,
                evenHourAndMinute = true,
                cropToCollectAvailable = true,
                businessDay = false,
                expectedResult = false
        )
        runWiringTestLogic(
                timeBetweenStartAndEnd = true,
                evenHourAndMinute = true,
                cropToCollectAvailable = true,
                businessDay = true,
                expectedResult = true
        )
    }
    @Test
    fun cropToCollectAvailable() {
        cropToCollectAvailableTestLogic(
                areaWithCrop = 0.005,
                expectedResult = true
        )
        cropToCollectAvailableTestLogic(
                areaWithCrop = 0.0,
                expectedResult = false
        )
    }
    @Test
    fun run() {
        // Prepare
        val simParamProv =
                AgriculturalSimParametersProvider(
                        File(
                                "src/test/resources/ch03/"+
                                "BasicAgriculturalSimulationRye.properties"
                        )
                )
        simParamProv.initAndValidate()
        val field = simParamProv.agents.find { it.id() == Field.ID } as
                DefaultAgent
        val shack = simParamProv.agents.find { it.id() == Shack.ID } as
                DefaultAgent
        shack.remove(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
                shack.amount(
                        AgriculturalSimParametersProvider.RESOURCE_SEEDS.id
                )
        )
        Assertions.assertThat(shack.amount(AgriculturalSimParametersProvider
                .RESOURCE_SEEDS.id)).isEqualTo(0.0)

        field.remove(
                AgriculturalSimParametersProvider.RESOURCE_EMPTY_AREA.id,
                field.amount(
                        AgriculturalSimParametersProvider.RESOURCE_EMPTY_AREA.id
                )
        )
        Assertions.assertThat(field.amount(AgriculturalSimParametersProvider
                .RESOURCE_EMPTY_AREA.id)).isZero
        field.put(AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_CROP.id,
                250000.0)
        field.put(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
                250000.0 * 0.3595)

        val time = 0L.millisToSimulationDateTime()
        val out = Process3(simParamProv)
        // Run method under test
        out.run(time)
        // Verify
        Assertions.assertThat(field.amount(AgriculturalSimParametersProvider
                .RESOURCE_EMPTY_AREA.id)).isEqualTo(17.77777777777778)
        Assertions.assertThat(field.amount(AgriculturalSimParametersProvider
                .RESOURCE_AREA_WITH_CROP.id)).isEqualTo(249982.22222222222)
        Assertions.assertThat(field.amount(AgriculturalSimParametersProvider
                .RESOURCE_SEEDS.id)).isEqualTo(89868.60888888889)
        Assertions.assertThat(shack.amount(AgriculturalSimParametersProvider
                .RESOURCE_SEEDS.id)).isEqualTo(6.391111111111111)
    }

    private fun cropToCollectAvailableTestLogic(areaWithCrop: Double,
                                                expectedResult: Boolean) {
        // Prepare
        val simParamProv =
                AgriculturalSimParametersProvider(
                        File(
                                "src/test/resources/ch03/BasicAgriculturalSimulationRye.properties"
                        )
                )
        simParamProv.initAndValidate()
        val field = simParamProv.agents.find { it.id() == Field.ID } as
                DefaultAgent
        field.put(AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_CROP.id,
                areaWithCrop)
        val out = Process3(simParamProv)
        // Run method under test
        val res = out.cropToCollectAvailable()
        // Verify
        Assertions.assertThat(res).isEqualTo(expectedResult)
    }

    private fun runWiringTestLogic(
            timeBetweenStartAndEnd: Boolean,
            evenHourAndMinute: Boolean,
            cropToCollectAvailable: Boolean,
            businessDay: Boolean,
            expectedResult: Boolean) {
        // Prepare
        val simParamProv =
                AgriculturalSimParametersProvider(
                        File(
                                "src/test/resources/ch03/BasicAgriculturalSimulationRye.properties"
                        )
                )
        simParamProv.initAndValidate()
        val time = 0L.millisToSimulationDateTime()
        val out = Mockito.spy(Process3(simParamProv))
        Mockito.doReturn(timeBetweenStartAndEnd).`when`(out)
                .timeBetweenStartAndEnd(time)
        Mockito.doReturn(evenHourAndMinute).`when`(out).evenHourAndMinute(time)
        Mockito.doReturn(cropToCollectAvailable).`when`(out)
                .cropToCollectAvailable()
        Mockito.doReturn(businessDay).`when`(out).businessDay(time)
        // Run method under test
        val res = out.timeToRun(time)
        // Verify
        Assertions.assertThat(res).isEqualTo(expectedResult)
    }
}