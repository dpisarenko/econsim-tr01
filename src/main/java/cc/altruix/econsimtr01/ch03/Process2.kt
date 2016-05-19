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

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 16.05.2016.
 */
class Process2(val simParamProv:AgriculturalSimParametersProvider) : IAction {
    val LOGGER = LoggerFactory.getLogger(Process2::class.java)
    val end  = simParamProv.data["Process2End"].toString()
            .parseDayMonthString()
    var field = simParamProv.agents.find { it.id() == Field.ID }
            as DefaultAgent
    override fun timeToRun(time: DateTime): Boolean =
            time.isEqual(end.toDateTime(time.year)) &&
            evenHourAndMinute(time)
    open internal fun evenHourAndMinute(time: DateTime): Boolean = time
            .evenHourAndMinute(0, 0)
    override fun run(time: DateTime) {
        val areaWithSeeds = field.amount(AgriculturalSimParametersProvider
                .RESOURCE_AREA_WITH_SEEDS.id)
        if (areaWithSeeds == 0.0) {
            LOGGER.error("There are no seeds on the field")
            return
        }
        val yieldPerSquareMeter = simParamProv
                .data["Process2YieldPerSquareMeter"].toString().toDouble()

        field.remove(
                AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_SEEDS.id,
                areaWithSeeds
        )
        field.put(
                AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_CROP.id,
                areaWithSeeds
        )
        field.put(
                AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
                areaWithSeeds * yieldPerSquareMeter
        )
    }
    override fun notifySubscribers(time: DateTime) {
    }
    override fun subscribe(subscriber: IActionSubscriber) {
    }
}