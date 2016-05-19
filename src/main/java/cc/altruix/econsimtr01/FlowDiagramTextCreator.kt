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