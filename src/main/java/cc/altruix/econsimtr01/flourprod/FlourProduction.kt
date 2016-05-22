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
import cc.altruix.econsimtr01.ch03.AgriculturalSimParametersProvider
import cc.altruix.econsimtr01.ch03.PropertiesFileSimParametersProvider
import cc.altruix.econsimtr01.ch03.Shack
import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
open class FlourProduction(val simParamProv:
    PropertiesFileSimParametersProvider) :
    IAction {
    val shack = simParamProv.agents.find { it.id() == Shack.ID }
        as DefaultAgent

    override fun timeToRun(time: DateTime): Boolean =
        businessDay(time) &&
        evenHourAndMinute(time) &&
        wheatInShack(shack)

    open internal fun wheatInShack(shack: DefaultAgent): Boolean {
        val grainInShack =
            shack.amount(AgriculturalSimParametersProvider.RESOURCE_SEEDS.id)
        val grainInputPerHour = simParamProv.data["MillThroughput"].
            toString().toDouble()
        return grainInShack >= grainInputPerHour
    }

    open internal fun evenHourAndMinute(time: DateTime): Boolean =
        time.evenHourAndMinute(8, 0)

    open internal fun businessDay(time: DateTime): Boolean =
        time.isBusinessDay()

    override fun run(time: DateTime) {
        // TODO: Test this
        // TODO: Implement this
    }

    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }
}
