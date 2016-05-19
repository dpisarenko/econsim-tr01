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

package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog

/**
 * Created by pisarenko on 20.04.2016.
 */
class TransitionReadinessColFunction : ITimeSeriesFieldFillerFunction {
    companion object {
        val moneyInSavingsAccountExtractor = MoneyInSavingsAccountColFunction()
        val peopleInListExtractor = SubscribersCohortColFunction(1)
        val softwareCompletionExtractor = PercentageResourceLevelColFunction("stacy", "r14", 480.0)
    }
    override fun invoke(prolog: Prolog, time: Long): String
            {
        if (enoughMoney(prolog, time) &&
                enoughPeopleInList(prolog, time) &&
                softwareComplete(prolog, time)) {
            return "yes"
        }
        return "no"
    }

    internal fun softwareComplete(prolog: Prolog, time: Long): Boolean =
            softwareCompletionExtractor.invoke(prolog, time).toDouble() >= 100.0

    internal fun enoughPeopleInList(prolog: Prolog, time: Long): Boolean =
            peopleInListExtractor.invoke(prolog, time).toDouble() >= 1000.0

    internal fun enoughMoney(prolog: Prolog, time: Long): Boolean =
            moneyInSavingsAccountExtractor.invoke(prolog, time).toDouble() >= 3000.0
}