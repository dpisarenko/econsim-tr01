package cc.altruix.econsimtr01

/**
 * Created by pisarenko on 05.04.2016.
 */
class FlowDiagramTextCreator {
    fun createFlowDiagramText(flows:List<ResourceFlow>):String {
        val builder = StringBuilder()
        builder.append("@startuml\n")
        appendTitle(builder)
        flows.sortedBy { x -> x.time }.forEach { flow ->
            builder.append("${flow.src.id()} -> ${flow.target.id()}: ${flow.res.name} ${flow.amt} ${flow.res.unit}\n")
            builder.append("note left: ${flow.time.toSimulationDateTimeString()}\n")
        }
        appendLegend(builder)
        builder.append("@enduml\n")
        return builder.toString()
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