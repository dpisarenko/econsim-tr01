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

    protected fun appendRow(builder: StringBuilder,
                            columns: Array<String>) {
        columns.forEach {
            builder.append("\"$it\";")
        }
        builder.newLine()
    }

}
