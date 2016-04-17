package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.DefaultTimeSeriesCreator
import cc.altruix.javaprologinterop.PlUtils
import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Sim2TimeSeriesCreator : DefaultTimeSeriesCreator() {
    companion object {
        val columns = arrayOf(
                "t [sec]",
                "Time",
                "Money @ Stacy",
                "Copies of software @ Target audience",
                "Total number of subscribers in the list",
                "Subscribers (1 interaction)",
                "Subscribers (2 interactions)",
                "Subscribers (3 interactions)",
                "Subscribers (4 interactions)",
                "Subscribers (5 interactions)",
                "Subscribers (6 interactions)",
                "Subscribers (7 or more interactions)"
        )
    }
    override fun prologToCsv(input: File): String {
        val builder = StringBuilder()
        appendRow(builder, columns)

        val prolog = PlUtils.createEngine()
        val times = extractTimes(input, prolog)

        times.forEach { t ->
            val data = calculateData(prolog, t)
            appendRow(builder, data)
        }

        return builder.toString()
    }

    private fun calculateData(prolog: Prolog, t: Long): Array<String> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
