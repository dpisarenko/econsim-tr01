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
import org.joda.time.DateTime
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 17.05.2016.
 */
class Process2Tests {
    @Test
    fun timeToRun() {
        timeToRunTestLogic(
                time = DateTime(0, 7, 4, 23, 59),
                expectedResult = false
        )
        timeToRunTestLogic(
                time = DateTime(0, 7, 5, 0, 0),
                expectedResult = true
        )
        timeToRunTestLogic(
                time = DateTime(0, 7, 5, 0, 1),
                expectedResult = false
        )
    }
    @Test
    fun run() {
        // Prepare
        val simParamProv =
                AgriculturalSimParametersProvider(
                        File(
                                "src/test/resources/ch03/BasicAgriculturalSimulationRye.properties"
                        )
                )
        simParamProv.initAndValidate()
        val field = simParamProv.agents.find { it.id() == Field.ID }
                as DefaultAgent
        Assertions.assertThat(field).isNotNull
        field.put(AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_SEEDS
                .id,
                250000.0)
        Assertions.assertThat(field.amount(AgriculturalSimParametersProvider
                .RESOURCE_SEEDS.id)).isZero
        val time = 0L.millisToSimulationDateTime()
        val out = Process2(simParamProv)
        // Run method under test
        out.run(time)
        // Verify
        Assertions.assertThat(
                field.amount(
                        AgriculturalSimParametersProvider.
                                RESOURCE_AREA_WITH_SEEDS.id
                )
        ).isEqualTo(0.0)
        Assertions.assertThat(
                field.amount(
                        AgriculturalSimParametersProvider.
                                RESOURCE_AREA_WITH_CROP.id
                )
        ).isEqualTo(250000.0)
        Assertions.assertThat(
                field.amount(
                        AgriculturalSimParametersProvider.
                                RESOURCE_SEEDS.id
                )
        ).isEqualTo(250000.0*0.3595)
    }

    private fun timeToRunTestLogic(time: DateTime,
                                   expectedResult: Boolean) {
        // Prepare
        val data = Properties()
        data["Process2End"] = "05.07"
        val simParamProv =
                AgriculturalSimParametersProviderWithPredefinedData(data)
        simParamProv.initAndValidate()
        simParamProv.agents.add(Field(simParamProv))
        val out = Process2(simParamProv)
        // Run method under test
        val res = out.timeToRun(time)
        // Verify
        Assertions.assertThat(res).isEqualTo(expectedResult)
    }
}