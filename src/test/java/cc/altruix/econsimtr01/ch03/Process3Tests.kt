/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
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
                expectedResult = false
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
        // TODO: Continue here
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
        val shack = simParamProv.agents.find { it.id() == Shack.ID } as
                DefaultAgent
        Assertions.assertThat(shack.amount(AgriculturalSimParametersProvider
                .RESOURCE_SEEDS.id)).isEqualTo(0.0)

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
                .RESOURCE_EMPTY_AREA.id)).isEqualTo(0.0)
        Assertions.assertThat(field.amount(AgriculturalSimParametersProvider
                .RESOURCE_AREA_WITH_CROP.id)).isEqualTo(0.0)
        Assertions.assertThat(field.amount(AgriculturalSimParametersProvider
                .RESOURCE_SEEDS.id)).isEqualTo(0.0)
        Assertions.assertThat(shack.amount(AgriculturalSimParametersProvider
                .RESOURCE_SEEDS.id)).isEqualTo(0.0)

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