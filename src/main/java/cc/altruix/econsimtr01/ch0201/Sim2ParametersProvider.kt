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
        val agentsPl = prolog.getResults("isAgent(X).", "X")
        val percentageOfReaders = prolog.extractSingleDouble("percentageOfReaders(X).", "X")
        val interactionsBeforePurchase= prolog.extractSingleInt("interactionsBeforePurchase(X).", "X")
        val percentageOfBuyers = prolog.extractSingleDouble("percentageOfBuyers(X).", "X")
        agentsPl
                .map { x -> x.removeSingleQuotes() }
                .map { when (it) {
                    "list" -> ListAgent(it,
                            percentageOfReaders,
                            interactionsBeforePurchase,
                            percentageOfBuyers)
                    else -> DefaultAgent(it)
                }}
                .forEach { this.agents.add(it) }
    }

    override fun createFlow(res: SolveInfo): PlFlow {
        // TODO: Test this
        val fdata = extractFlowData(res)
        when (fdata.id) {
            "f2" -> return createF2(fdata)
            "f3" -> return createF3(fdata)
            else -> return createFlow(fdata)
        }
    }

    private fun createF3(fdata: ExtractFlowDataResult): PlFlow {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun createF2(fdata: ExtractFlowDataResult): PlFlow {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}