package cc.altruix.econsimtr01

/**
 * Created by pisarenko on 05.04.2016.
 */
class FlowDiagramTextCreator(val resources:List<PlResource>) {
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
        val resource = resources.filter { x -> x.id.equals(res) }.first()
        if (resource != null) {
            return resource.unit
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