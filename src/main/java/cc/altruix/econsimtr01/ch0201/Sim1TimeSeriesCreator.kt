package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.newLine
import cc.altruix.econsimtr01.removeSingleQuotes
import cc.altruix.javaprologinterop.PlUtils
import java.io.File
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Sim1TimeSeriesCreator {
    fun prologToCsv(input: File): String {
        val builder = StringBuilder()
        appendRow(builder,
                "t [sec]",
                "Time",
                "Money @ Stacy",
                "Money in savings account")

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
            val moneyAtStacy = "" // TODO: Continue here
            val moneyInSavingsAccount = "" // TODO: Continue here

            appendRow(builder,
                    timeShort,
                    timeLong,
                    moneyAtStacy,
                    moneyInSavingsAccount)
        }
        // TODO: Continue here, integrate this class into simulation 1
        return builder.toString()
    }
    private fun extractTimeLongForm(prolog: Prolog, t: Long): String {
        return PlUtils
                .extractSingleStringFromQuery(prolog, "measurementTime($t, X).", "X")
                .removeSingleQuotes()
    }

    private fun appendRow(builder: StringBuilder,
                          timeShort: String,
                          timeLong: String,
                          moneyAtStacy: String,
                          moneyInSavingsAccount: String) {
        arrayOf(timeShort,
                timeLong,
                moneyAtStacy,
                moneyInSavingsAccount).forEach { col ->
            builder.append("\"$col\";")
        }
        builder.newLine()
    }
}
