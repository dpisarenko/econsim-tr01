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

package cc.altruix.econsimtr01

import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 05.04.2016.
 */
class FlowDiagramTextCreator(val resources:List<PlResource>) {
    val LOGGER = LoggerFactory.getLogger(FlowDiagramTextCreator::class.java)
    fun createFlowDiagramText(flows:List<ResourceFlow>):String {
        val builder = StringBuilder()
        if (flows.isNotEmpty() && resources.isNotEmpty()) {
            builder.append("@startuml\n")
            appendTitle(builder)
            flows.sortedBy { x -> x.time }.forEach { flow ->
                val unit = findUnit(flow.res)
                builder.append("${flow.src.id()} -> ${flow.target.id()}: ${flow.res} ${flow.amt} $unit\n")
                builder.append("note left: ${flow.time.toSimulationDateTimeString()}\n")
            }
            appendLegend(builder)
            builder.append("@enduml\n")
        }
        return builder.toString()
    }

    private fun findUnit(res: String): String {
        val resource = resources.filter { x -> x.id.equals(res.removeSingleQuotes()) }.firstOrNull()
        if (resource != null) {
            return resource.unit
        } else {
            LOGGER.error("Can't find resource '$res'")
        }
        return "?"
    }

    private fun appendLegend(builder: StringBuilder) {
        builder.append("legend right\n")
        builder.append("Copyright (C) Dmitri Pisarenko\n")
        builder.append("http://altruix.cc\n")
        builder.append("endlegend\n")
    }

    private fun appendTitle(builder: StringBuilder) {
        builder.append("title\n")
        builder.append("Flow diagram\n")
        builder.append("Copyright (C) Dmitri Pisarenko\n")
        builder.append("http://altruix.cc\n")
        builder.append("end title\n")
    }
}