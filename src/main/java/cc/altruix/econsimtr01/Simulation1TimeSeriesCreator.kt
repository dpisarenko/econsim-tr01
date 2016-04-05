package cc.altruix.econsimtr01

import cc.altruix.javaprologinterop.PlUtils
import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Simulation1TimeSeriesCreator {
    fun prologToCsv(input: File):String {
        val prolog = PlUtils.createEngine()
        PlUtils.loadPrologFiles(
                prolog,
                arrayOf(input.absolutePath)
        )
        val times = PlUtils.getResults(prolog,
                "measurementTime(Time, _).",
                "Time").sort()

        val builder = StringBuilder()
        appendRow(builder, "t [sec]", "Time", "Potatoes in store [kg]", "Number of days since last meal [day]")



        return builder.toString()
    }

    private fun appendRow(builder: StringBuilder,
                          timeShort: String,
                          timeLong: String,
                          potatoes: String,
                          daysSinceLastMeal: String) {

    }
}
