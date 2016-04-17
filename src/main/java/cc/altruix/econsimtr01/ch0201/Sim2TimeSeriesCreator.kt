package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.DefaultTimeSeriesCreator
import cc.altruix.econsimtr01.newLine
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


        return builder.toString()
    }

    private fun appendRow(builder: StringBuilder,
                          columns: Array<String>) {
        columns.forEach {
            builder.append("\"$it\";")
        }
        builder.newLine()
    }
}
