package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.getResults
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
            val moneyAtStacy = extractMoneyAtStacy(prolog, t)
            val moneyInSavingsAccount = extractMoneyInSavingsAccount(prolog, t)
            appendRow(builder,
                    timeShort,
                    timeLong,
                    moneyAtStacy,
                    moneyInSavingsAccount)
        }
        return builder.toString()
    }

    private fun extractMoneyInSavingsAccount(prolog: Prolog, time: Long): String =
            prolog.getResults("resourceLevel($time, savingsAccount, r2, Amount).", "Amount").first()

    private fun extractMoneyAtStacy(prolog: Prolog, time: Long): String =
            prolog.getResults("resourceLevel($time, stacy, r2, Amount).", "Amount").first()

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
