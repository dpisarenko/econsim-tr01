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

import alice.tuprolog.*
import alice.tuprolog.Int
import cc.altruix.econsimtr01.*
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.slf4j.LoggerFactory
import java.util.*

/**
 * Created by pisarenko on 08.04.2016.
 */
open class Sim1ParametersProvider(val theoryTxt: String) : ISimParametersProvider {
    val LOGGER = LoggerFactory.getLogger(Sim1ParametersProvider::class.java)
    var resources:List<PlResource>
        get
        private set
    override val flows:MutableList<PlFlow> = LinkedList()
        get
    override val agents:MutableList<IAgent> = LinkedList()
        get
    override val initialResourceLevels:MutableList<InitialResourceLevel> = LinkedList()
        get
    override val infiniteResourceSupplies:MutableList<InfiniteResourceSupply> = LinkedList()
        get
    override val transformations:MutableList<PlTransformation> = LinkedList()
        get

    init {
        val prolog = theoryTxt.toPrologTheory()
        this.resources = extractResources(prolog)
        readFlows(prolog)
        readAgents(prolog)
        readInitialResourceLevels(prolog)
        readInfiniteResourceSupplies(prolog)
        readTransformations(prolog)
    }

    private fun readInfiniteResourceSupplies(prolog: Prolog) {
        prolog.getResults("infiniteResourceSupply(Agent, Resource).", "Agent", "Resource").forEach { map ->
            infiniteResourceSupplies.add(
                    InfiniteResourceSupply(
                            map.get("Agent") ?: "",
                            map.get("Resource")?.removeSingleQuotes() ?: ""
                    )
            )
        }
    }

    private fun readInitialResourceLevels(prolog: Prolog) {
        prolog.getResults("initialResourceLevel(Agent, Resource, Amount).", "Agent", "Resource", "Amount")
                .forEach { map ->
                    initialResourceLevels.add(InitialResourceLevel(
                            map.get("Agent") ?: "",
                            map.get("Resource")?.removeSingleQuotes() ?: "",
                            map.get("Amount")?.toDouble() ?: 0.0
                    ))
                }
    }

    open protected fun readAgents(prolog: Prolog) {
        val agentsPl = prolog.getResults("isAgent(X).", "X")
        agentsPl
                .map { x -> x.removeSingleQuotes() }
                .forEach { apl ->
                    this.agents.add(DefaultAgent(apl.removeSingleQuotes()))
                }
    }

    protected fun readFlows(prolog: Prolog) {
        try {
            val reses = composeReses(
                    prolog,
                    "hasFlow(Id, Source, Target, Resource, Amount, Time)."
            )
            reses.forEach { this.flows.add(createFlow(it, prolog)) }
        } catch (exception: NoMoreSolutionException) {
            LOGGER.error("", exception)
        } catch (exception: MalformedGoalException) {
            LOGGER.error("", exception)
        }
    }

    protected fun readTransformations(prolog: Prolog) {
        try {
            val reses = composeReses(
                    prolog,
                    "hasTransformation(Id, Agent, InputAmount, InputResource, OutputAmount, OutputResource, Time)."
            )
            reses.forEach { this.transformations.add(createTransformation(it)) }
        } catch (exception: NoMoreSolutionException) {
            LOGGER.error("", exception)
        } catch (exception: MalformedGoalException) {
            LOGGER.error("", exception)
        }
    }

    private fun composeReses(prolog: Prolog, query: String): LinkedList<SolveInfo> {
        val reses = LinkedList<SolveInfo>()
        var res = prolog.solve(query)
        if (res.isSuccess()) {
            reses.add(res)
            while (prolog.hasOpenAlternatives()) {
                res = prolog.solveNext()
                reses.add(res)
            }
        }
        return reses
    }

    open fun createFlow(res: SolveInfo, prolog: Prolog): PlFlow {
        val fdata = extractFlowData(res)
        val flow = createFlow(fdata)
        return flow
    }

    open protected fun createFlow(fdata: ExtractFlowDataResult): PlFlow {
        return PlFlow(
                fdata.id,
                fdata.src,
                fdata.target,
                fdata.resource,
                fdata.amt,
                fdata.timeFunction
        )
    }
    open protected fun createTransformation(res: SolveInfo): PlTransformation {
        return PlTransformation(
                extractId(res),
                extractAgent(res, "Agent"),
                extractAmount(res, "InputAmount") ?: 0.0,
                extractResource(res, "InputResource"),
                extractAmount(res, "OutputAmount") ?: 0.0,
                extractResource(res, "OutputResource"),
                extractFiringFunction(res)
        )
    }


    data class ExtractFlowDataResult(val id:String,
                                     val src:String,
                                     val target:String,
                                     val resource:String,
                                     val amt:Double?,
                                     val timeFunction:(DateTime) -> Boolean)
    open fun extractFlowData(res:SolveInfo):ExtractFlowDataResult =
            ExtractFlowDataResult(extractId(res),
                    extractAgent(res, "Source"),
                    extractAgent(res, "Target"),
                    extractResource(res, "Resource"),
                    extractAmount(res, "Amount"),
                    extractFiringFunction(res))

    protected fun extractResource(res: SolveInfo, paramName: String) = res.getTerm(paramName).toString().removeSingleQuotes()

    protected fun extractAgent(res: SolveInfo, paramName: String) = res.getTerm(paramName).toString()

    protected fun extractId(res: SolveInfo) = res.getTerm("Id").toString()

    open fun extractFiringFunction(res: SolveInfo): (DateTime) -> Boolean {
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
            }
        }
        return timeFunction
    }

    fun oncePerMonthTriggerFunction(day: kotlin.Int): (DateTime) -> Boolean =
            {
                time:DateTime ->
                val curDay = time.dayOfMonth
                (day == curDay) &&
                        (time.hourOfDay == 0) &&
                        (time.minuteOfHour == 0) &&
                        (time.secondOfMinute == 0)
            }

    protected fun extractAmount(res: SolveInfo, paramName: String): Double? {
        val amtRaw = res.getTerm(paramName)
        val amt: Double?
        if (amtRaw is alice.tuprolog.Double) {
            amt = amtRaw.doubleValue()
        } else {
            amt = null
        }
        return amt
    }

    fun businessDaysTriggerFunction(): (DateTime) -> Boolean = { time: DateTime ->
        val dayOfWeek = time.dayOfWeek().get()
        if ((dayOfWeek == DateTimeConstants.SATURDAY) ||
                (dayOfWeek == DateTimeConstants.SUNDAY) ) {
            false
        } else if ((time.hourOfDay == 18) && (time.minuteOfHour == 0) && (time.secondOfMinute == 0)) {
            true
        } else {
            false
        }
    }

    private fun extractResources(prolog: Prolog): ArrayList<PlResource> {
        val resData = prolog.getResults("resource(Id, Name, Unit).",
                "Id", "Name", "Unit")
        val resList = ArrayList<PlResource>(resData.size)
        resData.forEach { map ->
            val res = PlResource(
                    map.get("Id").emptyIfNull().removeSingleQuotes(),
                    map.get("Name").emptyIfNull().removeSingleQuotes(),
                    map.get("Unit").emptyIfNull().removeSingleQuotes()
            )
            resList.add(res)
        }
        return resList
    }
}
