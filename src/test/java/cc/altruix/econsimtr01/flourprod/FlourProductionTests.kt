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

import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.ch03.AgriculturalSimParametersProvider
import cc.altruix.econsimtr01.ch03.Shack
import cc.altruix.econsimtr01.millisToSimulationDateTime
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class FlourProductionTests {
    @Test
    fun timeToRunWiring() {
        timeToRunWiringTestLogic(
            businessDay = false,
            evenHourAndMinute = false,
            wheatInSack = false,
            expectedResult = false
        )
        timeToRunWiringTestLogic(
            businessDay = true,
            evenHourAndMinute = false,
            wheatInSack = false,
            expectedResult = false
        )
        timeToRunWiringTestLogic(
            businessDay = true,
            evenHourAndMinute = true,
            wheatInSack = false,
            expectedResult = false
        )
        timeToRunWiringTestLogic(
            businessDay = true,
            evenHourAndMinute = true,
            wheatInSack = true,
            expectedResult = true
        )
    }
    @Test
    fun wheatInShack() {
        wheatInShackTestLogic(
            amountInShack = 10.0,
            expectedResult = false
        )
        wheatInShackTestLogic(
            amountInShack = 105.7172,
            expectedResult = false
        )
        wheatInShackTestLogic(
            amountInShack = 105.7173,
            expectedResult = true
        )
        wheatInShackTestLogic(
            amountInShack = 105.7174,
            expectedResult = true
        )
    }
    @Test
    fun runDoesTheTransactionIfEnoughGrainInShack() {
        runWiringTestLogic(
            grainToProcess = 10.0,
            expectedNumberOfConversionCalls = 1
        )
    }
    @Test
    fun runDoesntDoTheTransactionIfNotEnoughGrainInShack() {
        runWiringTestLogic(
            grainToProcess = 0.0,
            expectedNumberOfConversionCalls = 0
        )
    }
    @Test
    fun calculateGrainToProcessCorrectlyCalculatesMaxPossibleGrainInput() {
        // Prepare
        val simParamProv =
            FlourProductionSimulationParametersProvider(
                File("src/test/resources/flourprod/flourprodRye.properties")
            )
        simParamProv.initAndValidate()
        val shack = simParamProv.agents.find { it.id() == Shack.ID }
            as DefaultAgent
        shack.remove(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
            shack.amount(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id))
        Assertions.assertThat(shack.amount(AgriculturalSimParametersProvider
            .RESOURCE_SEEDS.id)).isZero
        shack.put(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id, 1000.0)
        val out = Mockito.spy(FlourProduction(simParamProv))
        // Run method under test
        val res = out.calculateGrainToProcess()
        // Verify
        Assertions.assertThat(res).isEqualTo(105.7173 * 8.0)
    }
    @Test
    fun calculateGrainToProcessSelectsTheRightMinimum() {
        // Prepare
        val simParamProv =
            FlourProductionSimulationParametersProvider(
                File("src/test/resources/flourprod/flourprodRye.properties")
            )
        simParamProv.initAndValidate()
        val shack = simParamProv.agents.find { it.id() == Shack.ID }
            as DefaultAgent
        shack.remove(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
            shack.amount(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id))
        Assertions.assertThat(shack.amount(AgriculturalSimParametersProvider
            .RESOURCE_SEEDS.id)).isZero
        shack.put(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id, 10.0)
        val out = Mockito.spy(FlourProduction(simParamProv))
        // Run method under test
        val res = out.calculateGrainToProcess()
        // Verify
        Assertions.assertThat(res).isEqualTo(10.0)
    }
    @Test
    fun convertGrainToFlour() {
        // Prepare
        val simParamProv =
            FlourProductionSimulationParametersProvider(
                File("src/test/resources/flourprod/flourprodRye.properties")
            )
        simParamProv.initAndValidate()
        val shack = simParamProv.agents.find { it.id() == Shack.ID }
            as DefaultAgent
        shack.remove(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
            shack.amount(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id))
        Assertions.assertThat(shack.amount(AgriculturalSimParametersProvider
            .RESOURCE_SEEDS.id)).isZero
        shack.put(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id, 100.0)
        val out = Mockito.spy(FlourProduction(simParamProv))
        // Run method under test
        out.convertGrainToFlour(100.0)
        // Verify
        Assertions.assertThat(shack.amount(AgriculturalSimParametersProvider
            .RESOURCE_SEEDS.id)).isZero
        Assertions.assertThat(shack.amount(
            FlourProductionSimulationParametersProvider.RESOURCE_FLOUR.id))
            .isEqualTo(0.9)
    }
    fun runWiringTestLogic(grainToProcess: Double,
                           expectedNumberOfConversionCalls:Int) {
        // Prepare
        val simParamProv =
            FlourProductionSimulationParametersProvider(
                File("src/test/resources/flourprod/flourprodRye.properties")
            )
        simParamProv.initAndValidate()
        val out = Mockito.spy(FlourProduction(simParamProv))
        Mockito.doReturn(grainToProcess).`when`(out).calculateGrainToProcess()
        Mockito.doNothing().`when`(out).convertGrainToFlour(grainToProcess)
        val time = 0L.millisToSimulationDateTime()
        // Run method under test
        out.run(time)
        // Verify
        Mockito.verify(out, Mockito.times(expectedNumberOfConversionCalls)).
            convertGrainToFlour(grainToProcess)
    }
    private fun wheatInShackTestLogic(
        amountInShack: Double,
        expectedResult: Boolean
    ) {
        // Prepare
        val simParamProv =
            FlourProductionSimulationParametersProvider(
                File("src/test/resources/flourprod/flourprodRye.properties")
            )
        simParamProv.initAndValidate()
        val shack = simParamProv.agents.find { it.id() == Shack.ID }
            as DefaultAgent
        shack.remove(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
            shack.amount(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id))
        Assertions.assertThat(shack.amount(AgriculturalSimParametersProvider
            .RESOURCE_SEEDS.id)).isZero
        shack.put(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
            amountInShack)
        val out = Mockito.spy(FlourProduction(simParamProv))
        // Run method under test
        val res = out.wheatInShack(shack)
        // Verify
        Assertions.assertThat(res).isEqualTo(expectedResult)
    }

    fun timeToRunWiringTestLogic(
        businessDay:Boolean,
        evenHourAndMinute:Boolean,
        wheatInSack:Boolean,
        expectedResult:Boolean
    ) {
        // Prepare
        val simParamProv =
            FlourProductionSimulationParametersProvider(File("someFile"))
        val shack = Shack()
        simParamProv.agents.add(shack)
        val out = Mockito.spy(FlourProduction(simParamProv))
        val time = 0L.millisToSimulationDateTime()
        Mockito.doReturn(businessDay).`when`(out).businessDay(time)
        Mockito.doReturn(evenHourAndMinute).`when`(out).evenHourAndMinute(time)
        Mockito.doReturn(wheatInSack).`when`(out).wheatInShack(shack)
        // Run method under test
        val res = out.timeToRun(time)
        // Verify
        Assertions.assertThat(res).isEqualTo(expectedResult)
    }
}
