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
import cc.altruix.econsimtr01.ch0201.ColumnDescriptor
import cc.altruix.javaprologinterop.PlUtils
import java.io.File
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
abstract class DefaultTimeSeriesCreator : ITimeSeriesCreator {
    open internal fun extractTimes(input: File, prolog: Prolog?): List<Long> {
        PlUtils.loadPrologFiles(
                prolog,
                arrayOf(input.absolutePath)
        )
        val times = PlUtils.getResults(prolog,
                "measurementTime(Time, _).",
                "Time").map { x -> x.toLong() }.toList()
        Collections.sort(times)
        return times
    }

    protected fun extractTimeLongForm(prolog: Prolog, t: Long): String =
            PlUtils.extractSingleStringFromQuery(prolog, "measurementTime($t, X).", "X")
                    .removeSingleQuotes()

    open internal fun appendRow(builder: StringBuilder,
                            columns: Array<String>) {
        columns.forEach {
            builder.append("\"$it\";")
        }
        builder.newLine()
    }

    open internal fun createStringBuilder() = StringBuilder()
    open internal fun appendRows(builder: StringBuilder,
                                 prolog: Prolog,
                                 times: List<Long>,
                                 columns: Array<ColumnDescriptor>) {
        times.forEach { t ->
            val data = calculateData(prolog, t, columns)
            appendRow(builder, data)
        }
    }

    open internal fun createPrologEngine() = PlUtils.createEngine()
    open internal fun appendHeader(builder: StringBuilder, cols: Array<ColumnDescriptor>) {
        appendRow(builder, cols.map { it.title }.toTypedArray())
    }

    open internal fun calculateData(prolog: Prolog, t: Long, columns: Array<ColumnDescriptor>): Array<String> {
        return columns.map { it.func(prolog, t) }.toTypedArray()
    }

}
