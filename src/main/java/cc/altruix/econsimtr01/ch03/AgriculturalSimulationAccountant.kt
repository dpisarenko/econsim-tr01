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

import cc.altruix.econsimtr01.AbstractAccountant2
import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ch0202.SimResRow
import cc.altruix.econsimtr01.evenHourAndMinute
import org.joda.time.DateTime

/**
 * Created by pisarenko on 17.05.2016.
 */
open class AgriculturalSimulationAccountant(resultsStorage: MutableMap<DateTime,
        SimResRow<AgriculturalSimulationRowField>>,
                                       scenarioName: String) :
        AbstractAccountant2<AgriculturalSimulationRowField>(resultsStorage,
                scenarioName) {
    override fun timeToMeasure(time: DateTime): Boolean =
            time.evenHourAndMinute(0, 0)
    override fun saveRowData(agents: List<IAgent>,
                         target: MutableMap<AgriculturalSimulationRowField,
                                 Double>) {
        target.put(AgriculturalSimulationRowField.SEEDS_IN_SHACK,
                calculateSeedsInShack(agents))
        target.put(AgriculturalSimulationRowField.FIELD_AREA_WITH_SEEDS,
                calculateFieldAreaWithSeeds(agents))
        target.put(AgriculturalSimulationRowField.EMPTY_FIELD_AREA,
                calculateEmptyFieldArea(agents))
        target.put(AgriculturalSimulationRowField.FIELD_AREA_WITH_CROP,
                calculateFieldAreaWithCrop(agents))
    }

    open internal fun calculateFieldAreaWithCrop(agents: List<IAgent>): Double
            = (agents.find { it.id() == Field.ID } as DefaultAgent).
            amount(
                    AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_CROP.id
            )

    open internal fun calculateEmptyFieldArea(agents: List<IAgent>): Double =
            (agents.find { it.id() == Field.ID } as DefaultAgent).
            amount(
                    AgriculturalSimParametersProvider.RESOURCE_EMPTY_AREA.id
            )

    open internal fun calculateFieldAreaWithSeeds(agents: List<IAgent>): Double
    = (agents.find { it.id() == Field.ID } as DefaultAgent).
            amount(
                    AgriculturalSimParametersProvider.
                            RESOURCE_AREA_WITH_SEEDS.id
            )

    open internal fun calculateSeedsInShack(agents: List<IAgent>): Double =
            (agents.find { it.id() == Shack.ID } as DefaultAgent).
                    amount(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id)
}
