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
    override val flows:MutableList<PlFlow> = LinkedList<PlFlow>()
        get
    override val agents:MutableList<IAgent> = LinkedList<IAgent>()
        get
    override val initialResourceLevels:MutableList<InitialResourceLevel> = LinkedList<InitialResourceLevel>()
        get
    override val infiniteResourceSupplies:MutableList<InfiniteResourceSupply> = LinkedList<InfiniteResourceSupply>()
        get

    init {
        val prolog = theoryTxt.toPrologTheory()
        this.resources = extractResources(prolog)
        readFlows(prolog)
        readAgents(prolog)
        readInitialResourceLevels(prolog)
        readInfiniteResourceSupplies(prolog)
    }

    private fun readInfiniteResourceSupplies(prolog: Prolog) {
        prolog.getResults("infiniteResourceSupply(Agent, Resource).", "Agent", "Resource").forEach { map ->
            infiniteResourceSupplies.add(
                    InfiniteResourceSupply(
                            map.get("Agent") ?: "",
                            map.get("Resource") ?: ""
                    )
            )
        }
    }

    private fun readInitialResourceLevels(prolog: Prolog) {
        prolog.getResults("initialResourceLevel(Agent, Resource, Amount).", "Agent", "Resource", "Amount")
                .forEach { map ->
                    initialResourceLevels.add(InitialResourceLevel(
                            map.get("Agent") ?: "",
                            map.get("Resource") ?: "",
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
            var res = prolog.solve("hasFlow(Id, Source, Target, Resource, Amount, Time).")
            if (res.isSuccess()) {
                this.flows.add(createFlow(res))
                while (prolog.hasOpenAlternatives()) {
                    res = prolog.solveNext()
                    this.flows.add(createFlow(res))
                }
            }

        } catch (exception: NoMoreSolutionException) {
            LOGGER.error("", exception)
        } catch (exception: MalformedGoalException) {
            LOGGER.error("", exception)
        }
    }

    open protected fun createFlow(res: SolveInfo): PlFlow {
        val fdata = extractFlowData(res)
        val flow = PlFlow(
                fdata.id,
                fdata.src,
                fdata.target,
                fdata.resource,
                fdata.amt,
                fdata.timeFunction
        )
        return flow
    }

    data class ExtractFlowDataResult(val id:String,
                                     val src:String,
                                     val target:String,
                                     val resource:String,
                                     val amt:Double?,
                                     val timeFunction:(DateTime) -> Boolean)
    protected fun extractFlowData(res:SolveInfo):ExtractFlowDataResult =
            ExtractFlowDataResult(extractId(res),
                    extractSource(res),
                    extractTarget(res),
                    extractResource(res),
                    extractAmount(res),
                    extractFiringFunction(res))

    protected fun extractResource(res: SolveInfo) = res.getTerm("Resource").toString()

    protected fun extractTarget(res: SolveInfo) = res.getTerm("Target").toString()

    protected fun extractSource(res: SolveInfo) = res.getTerm("Source").toString()

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

    protected fun extractAmount(res: SolveInfo): Double? {
        val amtRaw = res.getTerm("Amount")
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
