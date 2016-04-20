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
