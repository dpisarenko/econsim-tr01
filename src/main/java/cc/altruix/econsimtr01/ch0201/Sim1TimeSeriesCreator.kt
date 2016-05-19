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
