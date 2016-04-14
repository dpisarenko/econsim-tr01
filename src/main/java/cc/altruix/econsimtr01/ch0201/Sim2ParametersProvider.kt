package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Int
import alice.tuprolog.Prolog
import alice.tuprolog.SolveInfo
import alice.tuprolog.Struct
import cc.altruix.econsimtr01.*
import org.joda.time.DateTime

/**
 * Created by pisarenko on 14.04.2016.
 */
open class Sim2ParametersProvider(val theoryTxt2:String) :
        Sim1ParametersProvider(theoryTxt2){

    init {
        initAfterFlows()
    }

    protected fun initAfterFlows() {
        flows.filter { it.timeTriggerFunction is After }
                .map { it.timeTriggerFunction }
                .forEach { (it as After).connectToInitiatingFunctionFlow(flows) }
    }

    override fun extractFiringFunction(res: SolveInfo): (DateTime) -> Boolean {
        val timeFunctionPl = res.getTerm("Time")
        var timeFunction = { x: DateTime -> false }
        if (timeFunctionPl is Struct) {
            when (timeFunctionPl.name) {
                "businessDays" -> timeFunction = businessDaysTriggerFunction()
                "oncePerMonth" -> {
                    val day = (timeFunctionPl.getArg(0) as Int).intValue()
                    timeFunction = oncePerMonthTriggerFunction(day)
                }
                "daily" -> {
                    val hour = (timeFunctionPl.getArg(0) as Int).intValue()
                    val minute = (timeFunctionPl.getArg(1) as Int).intValue()
                    timeFunction = daily(hour, minute)
                }
                "oncePerWeek" -> {
                    val dayOfWeek = (timeFunctionPl.getArg(0) as Struct).name
                    timeFunction = OncePerWeek(dayOfWeek)
                }
                "after" -> {
                    val triggeringFlowId = (timeFunctionPl.getArg(0) as Struct).name
                    timeFunction = After(triggeringFlowId)
                }
            }
        }
        return timeFunction
    }
    override fun readAgents(prolog: Prolog) {
        // TODO: Test this
        val agentsPl = prolog.getResults("isAgent(X).", "X")
        agentsPl
                .map { x -> x.removeSingleQuotes() }
                .map { when (it) {
                    "list" -> List(it)
                    else -> DefaultAgent(it)
                }}
                .forEach { this.agents.add(it) }
    }

}