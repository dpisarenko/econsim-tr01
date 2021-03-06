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
        initListRelatedFlows(agents, flows)
        initFlowSubscriptions()
        initWhenResourceReachesLevel()
        attachTransformationsToAgents()
    }

    private fun attachTransformationsToAgents() {
        this.transformations.forEach {
            val agent = findAgent(it.agentId, this.agents)
            if ((agent != null) && (agent is DefaultAgent)) {
                agent.addTransformation(it)
            }
        }
        this.transformations.filter { it.timeTriggerFunction is After }
                .map { it.timeTriggerFunction }
                .forEach { (it as After).connectToInitiatingFunctionFlow(flows) }
    }

    protected open fun initFlowSubscriptions() {
        val f1 = flows.filter { (it.id == "f1") && (it is PlFlow) }.firstOrNull()
        if (f1 == null) {
            LOGGER.error("Could not find flow 'f1'")
            return
        }
        val list = findListAgent(agents)
        if (list == null) {
            LOGGER.error("Could not find list agent")
            return
        }
        f1.subscribe(list)
    }

    open fun initListRelatedFlows(agents: List<IAgent>, flws: List<PlFlow>) {
        val listAgent = findListAgent(agents)
        if (listAgent != null) {
            flws.filter { it is ListRelatedFlow }
                    .map { it as ListRelatedFlow }
                    .forEach { it.list = listAgent }
        } else {
            LOGGER.error("Can't find list agent")
        }
    }

    open fun findListAgent(agents: List<IAgent>): ListAgent? =
            agents.filter { it is ListAgent }
                    .map { it as ListAgent}
                    .firstOrNull()

    protected fun initAfterFlows() {
        flows.filter { it.timeTriggerFunction is After }
                .map { it.timeTriggerFunction }
                .forEach { (it as After).connectToInitiatingFunctionFlow(flows) }
    }

    open internal fun initWhenResourceReachesLevel() {
        flows.filter { it.timeTriggerFunction is WhenResourceReachesLevel }
            .map { it.timeTriggerFunction }
            .forEach { (it as WhenResourceReachesLevel).connectToInitiatingAgent(agents) }
    }


    open override fun extractFiringFunction(res: SolveInfo): (DateTime) -> Boolean {
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
                "whenResourceReachesLevel" -> {
                    timeFunction = createWhenResourceReachesLevel(timeFunctionPl)
                }
            }
        }
        return timeFunction
    }

    open internal fun createWhenResourceReachesLevel(timeFunctionPl: Struct): (DateTime) -> Boolean {
        val agent = (timeFunctionPl.getArg(0) as Struct).name
        val resource = (timeFunctionPl.getArg(1) as Struct).name
        val amount = (timeFunctionPl.getArg(2) as alice.tuprolog.Double).doubleValue()
        return WhenResourceReachesLevel(agent, resource, amount)
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

    override fun createFlow(res: SolveInfo, prolog: Prolog): PlFlow {
        val fdata = extractFlowData(res)
        when (fdata.id) {
            "f2" -> return createF2(fdata, prolog)
            "f3" -> return createF3(fdata, prolog)
            else -> return super.createFlow(fdata)
        }
    }

    open fun createF3(fdata: ExtractFlowDataResult, prolog: Prolog): PlFlow =
            F3Flow(fdata.id,
                    fdata.src,
                    fdata.target,
                    fdata.resource,
                    fdata.amt,
                    fdata.timeFunction)

    open fun createF2(fdata: ExtractFlowDataResult, prolog: Prolog): PlFlow {
        val priceOfOneCopyOfSoftware = readPriceOfOneCopyOfSoftware(prolog)
        val flow = F2Flow(fdata.id,
                fdata.src,
                fdata.target,
                fdata.resource,
                fdata.amt,
                fdata.timeFunction,
                priceOfOneCopyOfSoftware)
        return flow
    }

    open fun readPriceOfOneCopyOfSoftware(prolog: Prolog): Double {
        return prolog.extractSingleDouble(
                "priceOfOneCopyOfSoftware(X).",
                "X"
        )
    }
}
