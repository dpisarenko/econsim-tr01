package cc.altruix.econsimtr01

/**
 * Created by pisarenko on 05.04.2016.
 */
class FlowDiagramTextCreator {
    fun createFlowDiagramText(flows:List<ResourceFlow>):String {
        val builder = StringBuilder()
        builder.append("@startuml\n")
        flows.sortedBy { x -> x.time }.forEach { flow ->
            builder.append("${flow.src.id()} -> ${flow.target.id()}: ${flow.res.name} ${flow.amt} ${flow.res.unit}\n")
        }
        builder.append("@enduml\n")
        return builder.toString()
    }
}