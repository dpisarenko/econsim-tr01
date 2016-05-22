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

import cc.altruix.econsimtr01.*
import cc.altruix.econsimtr01.ch0202.SimResRow
import cc.altruix.econsimtr01.ch03.AgriculturalSimulationAccountant
import cc.altruix.econsimtr01.ch03.AgriculturalSimulationRowField
import cc.altruix.econsimtr01.ch03.Shack
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
open class FlourProductionSimulationAccountant(
    resultsStorage:
        MutableMap<DateTime, SimResRow<FlourProductionSimRowField>>,
        scenarioName: String,
        val asacc:AgriculturalSimulationAccountant =
            AgriculturalSimulationAccountant(
                resultsStorage =
                    HashMap<DateTime,
                        SimResRow<AgriculturalSimulationRowField>>(),
                scenarioName = ""
            )
) : AbstractAccountant2<FlourProductionSimRowField>(
        resultsStorage,
        scenarioName
    ) {
    val LOGGER = LoggerFactory.getLogger(
        FlourProductionSimulationAccountant::class.java
    )
    override fun timeToMeasure(time: DateTime): Boolean =
        time.evenHourAndMinute(0, 0)

    override fun saveRowData(
        agents: List<IAgent>,
        target: MutableMap<FlourProductionSimRowField, Double>) {
        target.put(FlourProductionSimRowField.SEEDS_IN_SHACK,
            asacc.calculateSeedsInShack(agents))
        target.put(FlourProductionSimRowField.FIELD_AREA_WITH_SEEDS,
            asacc.calculateFieldAreaWithSeeds(agents))
        target.put(FlourProductionSimRowField.EMPTY_FIELD_AREA,
            asacc.calculateEmptyFieldArea(agents))
        target.put(FlourProductionSimRowField.FIELD_AREA_WITH_CROP,
            asacc.calculateFieldAreaWithCrop(agents))
        target.put(FlourProductionSimRowField.FLOUR_IN_SHACK,
            calculateFieldInShack(agents))
    }

    open internal fun calculateFieldInShack(agents: List<IAgent>): Double {
        val shack = findAgent(Shack.ID, agents) as DefaultAgent
        if (shack == null) {
            LOGGER.error("Can't find the shack")
            return 0.0
        }
        return shack.amount(FlourProductionSimulationParametersProvider
            .RESOURCE_FLOUR.id)
    }
}
