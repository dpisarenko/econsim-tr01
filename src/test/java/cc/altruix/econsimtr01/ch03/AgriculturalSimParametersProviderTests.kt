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
import org.fest.assertions.Assertions
import org.junit.Test
import java.io.File

/**
 * Created by pisarenko on 14.05.2016.
 */
class AgriculturalSimParametersProviderTests {
    @Test
    fun ryeSimulationParametersCorrect() {
        simulationParametersCorrectnessTestLogic(
                "BasicAgriculturalSimulationRye.properties"
        )
    }
    @Test
    fun wheatSimulationParametersCorrect() {
        simulationParametersCorrectnessTestLogic(
                "BasicAgriculturalSimulationWheat.properties"
        )
    }
    @Test
    fun initAndValidateInitializesEmptyAreaOfField() {
        // Prepare
        val out = AgriculturalSimParametersProvider(
                File(
                        "src/test/resources/ch03/"+
                        "BasicAgriculturalSimulationRye.properties"
                )
        )
        // Run method under test
        out.initAndValidate()
        // Verify
        val field = out.agents.find { it.id() == Field.ID } as DefaultAgent
        Assertions.assertThat(field.amount(AgriculturalSimParametersProvider
                .RESOURCE_EMPTY_AREA.id)).isEqualTo(out.data["SizeOfField"]
                .toString().toDouble())
    }
    @Test
    fun initAndValidateInitializesSeedsInShack() {
        // Prepare
        val out = AgriculturalSimParametersProvider(
                File(
                        "src/test/resources/ch03/"+
                                "BasicAgriculturalSimulationRye.properties"
                )
        )
        // Run method under test
        out.initAndValidate()
        // Verify
        val shack = out.agents.find { it.id() == Shack.ID } as DefaultAgent
        Assertions.assertThat(shack.amount(AgriculturalSimParametersProvider
                .RESOURCE_SEEDS.id)).isEqualTo(out.data["InitialSeedQuantity"]
                .toString().toDouble())
    }
    @Test
    fun initialSeedQuantityIsParsedCorrectly() {
        initialSeedQuantityIsParsedCorrectlyTestLogic(
                "src/test/resources/ch03/"+
                "BasicAgriculturalSimulationRye.properties", 28.305
        )
        initialSeedQuantityIsParsedCorrectlyTestLogic(
                "src/test/resources/ch03/"+
                        "BasicAgriculturalSimulationWheat.properties", 20.3
        )
    }

    private fun initialSeedQuantityIsParsedCorrectlyTestLogic(
            fileName: String,
            expectedResult: Double) {
        val out = AgriculturalSimParametersProvider(File(fileName))
        out.initAndValidate()
        Assertions.assertThat(out.data["InitialSeedQuantity"].toString()
                .toDouble()).isEqualTo(expectedResult)
    }

    private fun simulationParametersCorrectnessTestLogic(fileName: String) {
        val out = AgriculturalSimParametersProvider(
                File("src/test/resources/ch03/$fileName")
        )
        out.initAndValidate()
        Assertions.assertThat(out.validity.message).isEmpty()
        Assertions.assertThat(out.validity.valid).isTrue()
        Assertions.assertThat(out.agents.size).isEqualTo(3)
        val farmers = out.agents.find { it.id() == Farmers.ID} as DefaultAgent
        Assertions.assertThat(farmers.actions.size).isEqualTo(2)
        val field = out.agents.find { it.id() == Field.ID } as DefaultAgent
        Assertions.assertThat(field.actions.size).isEqualTo(1)
        Assertions.assertThat(out.agents.find { it.id() == Shack.ID }).isNotNull
    }
}
