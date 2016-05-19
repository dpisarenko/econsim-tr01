/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ch0202.SimResRow
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * Created by pisarenko on 17.05.2016.
 */
class AgriculturalSimulationAccountantTests {
    @Test
    fun calculateFieldAreaWithCrop() {
        val simParamProv =
                AgriculturalSimParametersProviderWithPredefinedData(
                        Properties()
                )
        val field = Field(simParamProv)
        field.put(AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_CROP.id,
                123.45)
        val agents = listOf(field)
        val out = createObjectUnderTest()
        Assertions.assertThat(out.calculateFieldAreaWithCrop(agents))
                .isEqualTo(123.45)
    }
    @Test
    fun calculateEmptyFieldArea() {
        val simParamProv =
                AgriculturalSimParametersProviderWithPredefinedData(
                        Properties()
                )
        val field = Field(simParamProv)
        field.put(AgriculturalSimParametersProvider.RESOURCE_EMPTY_AREA.id,
                123.45)
        val agents = listOf(field)
        val out = createObjectUnderTest()
        Assertions.assertThat(out.calculateEmptyFieldArea(agents))
                .isEqualTo(123.45)
    }
    @Test
    fun calculateFieldAreaWithSeeds() {
        val simParamProv =
                AgriculturalSimParametersProviderWithPredefinedData(
                        Properties()
                )
        val field = Field(simParamProv)
        field.put(AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_SEEDS.id,
                123.45)
        val agents = listOf(field)
        val out = createObjectUnderTest()
        Assertions.assertThat(out.calculateFieldAreaWithSeeds(agents))
                .isEqualTo(123.45)
    }
    @Test
    fun calculateSeedsInShack() {
        val shack = Shack()
        shack.put(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
                123.45)
        val agents = listOf(shack)
        val out = createObjectUnderTest()
        Assertions.assertThat(out.calculateSeedsInShack(agents))
                .isEqualTo(123.45)
    }
    @Test
    fun saveRowDataWiring() {
        // Prepare
        val resultsStorage = HashMap<DateTime,
                SimResRow<AgriculturalSimulationRowField>>()
        val out = Mockito.spy(
                AgriculturalSimulationAccountant(
                        resultsStorage,
                        "scenario"
                )
        )
        val agents = emptyList<IAgent>()
        Mockito.doReturn(1.0).`when`(out).calculateSeedsInShack(agents)
        Mockito.doReturn(2.0).`when`(out).calculateFieldAreaWithSeeds(agents)
        Mockito.doReturn(3.0).`when`(out).calculateEmptyFieldArea(agents)
        Mockito.doReturn(4.0).`when`(out).calculateFieldAreaWithCrop(agents)
        val target = HashMap<AgriculturalSimulationRowField, Double>()
        // Run method under test
        out.saveRowData(agents, target)
        // Verify
        Assertions.assertThat(target[AgriculturalSimulationRowField
                .SEEDS_IN_SHACK]).isEqualTo(1.0)
        Assertions.assertThat(target[AgriculturalSimulationRowField
                .FIELD_AREA_WITH_SEEDS]).isEqualTo(2.0)
        Assertions.assertThat(target[AgriculturalSimulationRowField
                .EMPTY_FIELD_AREA]).isEqualTo(3.0)
        Assertions.assertThat(target[AgriculturalSimulationRowField
                .FIELD_AREA_WITH_CROP]).isEqualTo(4.0)
    }

    private fun createObjectUnderTest(): AgriculturalSimulationAccountant {
        val out = AgriculturalSimulationAccountant(HashMap<DateTime,
                SimResRow<AgriculturalSimulationRowField>>(), "scenario")
        return out
    }
}
