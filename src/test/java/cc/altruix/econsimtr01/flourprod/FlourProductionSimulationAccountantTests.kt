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

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ch0202.SimResRow
import cc.altruix.econsimtr01.ch03.AgriculturalSimulationAccountant
import cc.altruix.econsimtr01.ch03.Shack
import cc.altruix.econsimtr01.mock
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class FlourProductionSimulationAccountantTests {
    @Test
    fun saveRowDataWiring() {
        // Prepare
        val asacc = mock<AgriculturalSimulationAccountant>()
        val out = Mockito.spy(
            FlourProductionSimulationAccountant(
                resultsStorage =
                HashMap<DateTime, SimResRow<FlourProductionSimRowField>>(),
                scenarioName = "",
                asacc = asacc
            )
        )
        val agents = emptyList<IAgent>()
        Mockito.`when`(asacc.calculateSeedsInShack(agents)).thenReturn(1.0)
        Mockito.`when`(asacc.calculateFieldAreaWithSeeds(agents)).
            thenReturn(2.0)
        Mockito.`when`(asacc.calculateEmptyFieldArea(agents)).
            thenReturn(3.0)
        Mockito.`when`(asacc.calculateFieldAreaWithCrop(agents)).
            thenReturn(4.0)
        Mockito.doReturn(5.0).`when`(out).calculateFieldInShack(agents)
        val target = HashMap<FlourProductionSimRowField, Double>()
        // Run method under test
        out.saveRowData(agents, target)
        // Verify
        Mockito.verify(asacc).calculateSeedsInShack(agents)
        Mockito.verify(asacc).calculateFieldAreaWithSeeds(agents)
        Mockito.verify(asacc).calculateEmptyFieldArea(agents)
        Mockito.verify(asacc).calculateFieldAreaWithCrop(agents)
        Mockito.verify(out).calculateFieldInShack(agents)
        Assertions.assertThat(target[FlourProductionSimRowField
            .SEEDS_IN_SHACK]).isEqualTo(1.0)
        Assertions.assertThat(target[FlourProductionSimRowField
            .FIELD_AREA_WITH_SEEDS]).isEqualTo(2.0)
        Assertions.assertThat(target[FlourProductionSimRowField
            .EMPTY_FIELD_AREA]).isEqualTo(3.0)
        Assertions.assertThat(target[FlourProductionSimRowField
            .FIELD_AREA_WITH_CROP]).isEqualTo(4.0)
        Assertions.assertThat(target[FlourProductionSimRowField
            .FLOUR_IN_SHACK]).isEqualTo(5.0)
    }
    @Test
    fun calculateFieldInShack() {
        // Prepare
        val asacc = mock<AgriculturalSimulationAccountant>()
        val out = Mockito.spy(
            FlourProductionSimulationAccountant(
                resultsStorage =
                HashMap<DateTime, SimResRow<FlourProductionSimRowField>>(),
                scenarioName = "",
                asacc = asacc
            )
        )
        val shack = Shack()
        shack.put(FlourProductionSimulationParametersProvider.RESOURCE_FLOUR
            .id, 12.34)
        val agents = listOf<IAgent>(shack)
        // Run method under test
        val res = out.calculateFieldInShack(agents)
        // Verify
        Assertions.assertThat(res).isEqualTo(12.34)
    }
}
