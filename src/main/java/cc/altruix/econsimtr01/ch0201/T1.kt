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

import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.PlTransformation
import cc.altruix.econsimtr01.findAgent
import org.joda.time.DateTime

/**
 * Created by pisarenko on 22.04.2016.
 */
class T1(id:String,
         agentId:String,
         inputAmount:Double,
         inputResourceId:String,
         outputAmount:Double,
         outputResourceId:String,
         timeTriggerFunction: (DateTime) -> Boolean) :
        PlTransformation(
                id,
                agentId,
                inputAmount,
                inputResourceId,
                outputAmount,
                outputResourceId,
                timeTriggerFunction
        ) {
    override fun timeToRun(time: DateTime): Boolean = timeTriggerFunction(time) &&
            softwareNotComplete()

    private fun softwareNotComplete(): Boolean {
        val stacy = findAgent("stacy", agents)
        if ((stacy != null) && (stacy is DefaultAgent)) {
            return stacy.amount("r14") < 480.0
        }
        return false
    }
}