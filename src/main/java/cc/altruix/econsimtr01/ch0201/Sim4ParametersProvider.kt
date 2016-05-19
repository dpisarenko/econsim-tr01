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
import alice.tuprolog.SolveInfo
import cc.altruix.econsimtr01.*

/**
 * Created by pisarenko on 19.04.2016.
 */
open class Sim4ParametersProvider(theoryTxt2:String) :
        Sim2ParametersProvider(theoryTxt2) {
    override fun createFlow(res: SolveInfo, prolog: Prolog): PlFlow =
            super.createFlow(extractFlowData(res))
    override fun readAgents(prolog: Prolog) {
        val agentsPl = prolog.getResults("isAgent(X).", "X")
        agentsPl
                .map { x -> x.removeSingleQuotes() }
                .map { DefaultAgent(it) }
                .forEach { this.agents.add(it) }

    }
    override open fun initListRelatedFlows(agents: List<IAgent>, flws: List<PlFlow>) { }

    override open fun initFlowSubscriptions() { }

    override open fun createTransformation(res: SolveInfo): PlTransformation {
        val id = extractId(res)

        if (id == "t1") {
            return T1(id,
                    extractAgent(res, "Agent"),
                    extractAmount(res, "InputAmount") ?: 0.0,
                    extractResource(res, "InputResource"),
                    extractAmount(res, "OutputAmount") ?: 0.0,
                    extractResource(res, "OutputResource"),
                    extractFiringFunction(res))
        }

        return PlTransformation(
                id,
                extractAgent(res, "Agent"),
                extractAmount(res, "InputAmount") ?: 0.0,
                extractResource(res, "InputResource"),
                extractAmount(res, "OutputAmount") ?: 0.0,
                extractResource(res, "OutputResource"),
                extractFiringFunction(res)
        )
    }

}