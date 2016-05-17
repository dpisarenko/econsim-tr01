/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
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
        val data = Properties()
        data["Process2YieldPerSquareMeter"] = "0.3595"
        data["Process2End"] = "05.07"
        val simParamProv =
                AgriculturalSimParametersProvider(
                        File(
                                "src/test/resources/ch03/BasicAgriculturalSimulationRye.properties"
                        )
                )
        simParamProv.initAndValidate()
        simParamProv.agents.add(Field(simParamProv))
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
        val out = Process2(simParamProv)
        // Run method under test
        val res = out.timeToRun(time)
        // Verify
        Assertions.assertThat(res).isEqualTo(expectedResult)
    }
}