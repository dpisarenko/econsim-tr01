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
