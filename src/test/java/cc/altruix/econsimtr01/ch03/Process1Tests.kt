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

import cc.altruix.econsimtr01.millisToSimulationDateTime
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Process1Tests {
    @Test
    fun timeToRunWiring() {
        timeToRunWiringTestLogic(timeBetweenStartAndEnd = false,
                evenHourAndMinute = false,
                fieldNotNull = false,
                businessDay = false,
                expectedResult = false
        )
        timeToRunWiringTestLogic(timeBetweenStartAndEnd = true,
                evenHourAndMinute = false,
                fieldNotNull = false,
                businessDay = false,
                expectedResult = false
        )
        timeToRunWiringTestLogic(timeBetweenStartAndEnd = false,
                evenHourAndMinute = true,
                fieldNotNull = false,
                businessDay = false,
                expectedResult = false
        )
        timeToRunWiringTestLogic(timeBetweenStartAndEnd = false,
                evenHourAndMinute = false,
                fieldNotNull = true,
                businessDay = false,
                expectedResult = false
        )
        timeToRunWiringTestLogic(timeBetweenStartAndEnd = true,
                evenHourAndMinute = true,
                fieldNotNull = true,
                businessDay = false,
                expectedResult = false
        )
        timeToRunWiringTestLogic(timeBetweenStartAndEnd = true,
                evenHourAndMinute = true,
                fieldNotNull = true,
                businessDay = true,
                expectedResult = true
        )
    }
    fun timeToRunWiringTestLogic(timeBetweenStartAndEnd: Boolean,
                                 evenHourAndMinute: Boolean,
                                 fieldNotNull: Boolean,
                                 businessDay: Boolean,
                                 expectedResult: Boolean) {
        // Prepare
        val data = Properties()
        data["Process1Start"] = "30.08"
        data["Process1End"] = "30.10"
        val simParamProv =
                AgriculturalSimParametersProviderWithPredefinedData(data)
        simParamProv.initAndValidate()
        val field = Field(simParamProv)
        simParamProv.agents.add(field)
        simParamProv.agents.add(Shack())
        val out = Mockito.spy(Process1(simParamProv))
        val time = 0L.millisToSimulationDateTime()
        Mockito.doReturn(timeBetweenStartAndEnd).`when`(out)
                .timeBetweenStartAndEnd(time)
        Mockito.doReturn(evenHourAndMinute).`when`(out).evenHourAndMinute(time)
        Mockito.doReturn(fieldNotNull).`when`(out).fieldNotFull(field)
        Mockito.doReturn(businessDay).`when`(out).businessDay(time)
        // Run method under test
        val res = out.timeToRun(time)
        // Verify
        Assertions.assertThat(res).isEqualTo(expectedResult)
    }
    @Test
    fun initFindsField() {
        val data = Properties()
        data["Process1Start"] = "30.08"
        data["Process1End"] = "30.10"
        val simParamProv =
                AgriculturalSimParametersProviderWithPredefinedData(data)
        simParamProv.initAndValidate()
        val field = Field(simParamProv)
        simParamProv.agents.add(field)
        simParamProv.agents.add(Farmers(simParamProv))
        simParamProv.agents.add(Shack())
        val out = Process1(simParamProv)
        Assertions.assertThat(out.field).isSameAs(field)
    }
    @Test
    fun fieldNotFull() {
        fieldNotFullTestLogic(sizeOfField = "250000",
                areaWithSeeds = 249999.99,
                expectedResult = true)
        fieldNotFullTestLogic(sizeOfField = "250000",
                areaWithSeeds = 250000.0,
                expectedResult = false)
        fieldNotFullTestLogic(sizeOfField = "250000",
                areaWithSeeds = 250000.1,
                expectedResult = false)
    }
    @Test
    fun run() {
        // Prepare
        val data = Properties()
        data["NumberOfWorkers"] = "1"
        data["LaborPerBusinessDay"] = "8"
        data["Process1EffortInSquareMeters"] = "0.44"
        data["Process1QuantityOfSeeds"] = "0.0629"
        data["SizeOfField"] = "250000"
        data["Process1Start"] = "30.08"
        data["Process1End"] = "30.10"
        val simParamProv =
                AgriculturalSimParametersProviderWithPredefinedData(data)
        simParamProv.initAndValidate()
        val field = Field(simParamProv)
        field.put(AgriculturalSimParametersProvider.RESOURCE_EMPTY_AREA.id,
                250000.0)
        val shack = Shack()
        shack.put(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
                10.0)
        simParamProv.agents.add(field)
        simParamProv.agents.add(shack)
        val time = 0L.millisToSimulationDateTime()
        val out = Process1(simParamProv)
        Assertions.assertThat(
                field.amount(
                        AgriculturalSimParametersProvider.RESOURCE_EMPTY_AREA.id
                )
        ).isEqualTo(250000.0)
        Assertions.assertThat(
                field.amount(
                        AgriculturalSimParametersProvider.
                                RESOURCE_AREA_WITH_SEEDS.id
                )
        ).isEqualTo(0.0)
        Assertions.assertThat(
                shack.amount(
                        AgriculturalSimParametersProvider.RESOURCE_SEEDS.id
                )
        ).isEqualTo(10.0)
        // Run method under test
        out.run(time)
        // Verify
        Assertions.assertThat(
                field.amount(
                        AgriculturalSimParametersProvider.RESOURCE_EMPTY_AREA.id
                )
        ).isEqualTo(249981.81818181818)
        Assertions.assertThat(
                field.amount(
                        AgriculturalSimParametersProvider.
                                RESOURCE_AREA_WITH_SEEDS.id
                )
        ).isEqualTo(18.181818181818183)
        Assertions.assertThat(
                shack.amount(
                        AgriculturalSimParametersProvider.RESOURCE_SEEDS.id
                )
        ).isEqualTo(8.856363636363636)
    }
    private fun fieldNotFullTestLogic(sizeOfField:String,
                                      areaWithSeeds:Double,
                                      expectedResult:Boolean) {
        // Prepare
        val data = Properties()
        data["Process1Start"] = "30.08"
        data["Process1End"] = "30.10"
        data["SizeOfField"] = sizeOfField
        val simParamProv =
                AgriculturalSimParametersProviderWithPredefinedData(data)
        simParamProv.initAndValidate()
        val field = Field(simParamProv)
        field.put(
                AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_SEEDS.id,
                areaWithSeeds
        )
        simParamProv.agents.add(field)
        simParamProv.agents.add(Shack())
        val out = Process1(simParamProv)
        // Run method under test
        val res = out.fieldNotFull(field)
        // Verify
        Assertions.assertThat(res).isEqualTo(expectedResult)
    }
}
