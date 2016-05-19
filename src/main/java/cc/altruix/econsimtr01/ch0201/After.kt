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

import cc.altruix.econsimtr01.PlFlow
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 14.04.2016.
 */
open class After(val flowId:String) : (DateTime) -> Boolean {
    val LOGGER = LoggerFactory.getLogger(After::class.java)
    var nextFireTime:DateTime? = null

    init {
        reset()
    }

    private fun reset() {
        nextFireTime = null
    }

    override fun invoke(p1: DateTime): Boolean {
        val fire = ((nextFireTime != null) &&  p1.isEqual(nextFireTime))
        if (fire) {
            reset()
        }
        return fire
    }

    fun connectToInitiatingFunctionFlow(flows:MutableList<PlFlow>) {
        val initiatingFlow = flows.filter { flowId.equals(it.id) }.firstOrNull()
        if (initiatingFlow != null) {
            initiatingFlow.addFollowUpFlow(this)
        } else {
            LOGGER.error("Can't find flow '$flowId'")
        }
    }
    open fun updateNextFiringTime(time: DateTime) {
        this.nextFireTime = time.plusMinutes(1)
    }
}
