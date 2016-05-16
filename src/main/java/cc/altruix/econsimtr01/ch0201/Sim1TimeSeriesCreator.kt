/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.DefaultTimeSeriesCreator
import cc.altruix.econsimtr01.getResults
import cc.altruix.econsimtr01.newLine
import cc.altruix.javaprologinterop.PlUtils
import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Sim1TimeSeriesCreator : DefaultTimeSeriesCreator() {
    override fun prologToCsv(input: File): String {
        val builder = StringBuilder()
        appendRow(builder,
                "t [sec]",
                "Time",
                "Money @ Stacy",
                "Money in savings account")

        val prolog = PlUtils.createEngine()
        val times = extractTimes(input, prolog)

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
