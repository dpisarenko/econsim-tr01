package cc.altruix.econsimtr01

import alice.tuprolog.Prolog
import cc.altruix.javaprologinterop.PlUtils
import java.io.File
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Simulation1TimeSeriesCreator {
    fun prologToCsv(input: File):String {
        val builder = StringBuilder()
        appendRow(builder, "t [sec]", "Time", "Potatoes in store [kg]", "Number of days since last meal [day]")

        val prolog = PlUtils.createEngine()
        PlUtils.loadPrologFiles(
                prolog,
                arrayOf(input.absolutePath)
        )
        val times = PlUtils.getResults(prolog,
                "measurementTime(Time, _).",
                "Time").map { x -> x.toLong() }.toList()
        Collections.sort(times)

        times.forEach { t ->
            val timeShort = t.toString()
            val timeLong = extractTimeLongForm(prolog, t)
            val potatoes = extractPotatoCount(prolog, t)
            val daysSinceLastMeal = extractDaySinceLastMeal(prolog, t)

            appendRow(builder,
                    timeShort,
                    timeLong,
                    potatoes,
                    daysSinceLastMeal)
        }

        return builder.toString()
    }

    private fun extractDaySinceLastMeal(prolog: Prolog, t: Long): String {
        return PlUtils.extractSingleStringFromQuery(prolog, "daysWithoutEating($t, X).", "X")
    }

    private fun extractPotatoCount(prolog: Prolog, t: Long): String {
        return PlUtils.extractSingleStringFromQuery(prolog, "resourceAvailable($t, 'POTATO', X).", "X")
    }

    private fun extractTimeLongForm(prolog: Prolog, t: Long): String {
        return PlUtils
                .extractSingleStringFromQuery(prolog, "measurementTime($t, X).", "X")
                .removeSingleQuotes()
    }

    private fun appendRow(builder: StringBuilder,
                          timeShort: String,
                          timeLong: String,
                          potatoes: String,
                          daysSinceLastMeal: String) {
        arrayOf(timeShort,
                timeLong,
                potatoes,
                daysSinceLastMeal).forEach { col ->
            builder.append("\"$col\";")
        }
        builder.newLine()
    }
}
