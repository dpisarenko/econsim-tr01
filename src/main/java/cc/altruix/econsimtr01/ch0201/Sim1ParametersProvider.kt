package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.*
import cc.altruix.econsimtr01.*
import org.joda.time.DateTimeConstants
import org.slf4j.LoggerFactory
import java.util.*

/**
 * Created by pisarenko on 08.04.2016.
 */
class Sim1ParametersProvider(val theoryTxt: String) {
    val LOGGER = LoggerFactory.getLogger(Sim1ParametersProvider::class.java)
    var resources:List<PlResource>
        get
        private set
    val flows:MutableList<PlFlow> = LinkedList<PlFlow>()
        get

    init {
        val prolog = theoryTxt.toPrologTheory()
        this.resources = extractResources(prolog)

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

    protected fun createFlow(res: SolveInfo): PlFlow {
        val id = res.getTerm("Id").toString()
        val src = res.getTerm("Source").toString()
        val target = res.getTerm("Target").toString()
        val resource = res.getTerm("Resource").toString()
        val amtRaw = res.getTerm("Amount")
        val amt: Double?
        if (amtRaw is alice.tuprolog.Double) {
            amt = amtRaw.doubleValue()
        } else {
            amt = null
        }

        val timeFunctionPl = res.getTerm("Time")
        var timeFunction = { x: Long -> false }
        if (timeFunctionPl is Struct) {
            if ("businessDays".equals(timeFunctionPl.name)) {
                timeFunction = businessDaysTriggerFunction()
            }
        }

        val flow = PlFlow(
                id,
                src,
                target,
                resource,
                amt,
                timeFunction
        )
        return flow
    }

    fun businessDaysTriggerFunction(): (Long) -> Boolean {
        return { x: Long ->
            val time = x.millisToSimulationDateTime()
            val dayOfWeek = time.dayOfWeek().get()
            if ((dayOfWeek == DateTimeConstants.SATURDAY) ||
                    (dayOfWeek == DateTimeConstants.SUNDAY) ) {
                false
            }
            if ((time.hourOfDay == 18) && (time.minuteOfHour == 0)) {
                true
            }
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
