package cc.altruix.econsimtr01

import alice.tuprolog.Prolog
import alice.tuprolog.interfaces.IProlog
import cc.altruix.javaprologinterop.PlUtils
import net.sourceforge.plantuml.SourceStringReader
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Period
import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
fun secondsToDuration(seconds: Long) = Duration(0, seconds * 1000)

fun secondsToPeriod(seconds: Long) = Duration(0, seconds * 1000).toPeriod()

fun composeHourMinuteFiringFunction(hours:Int, minutes:Int): (Long) -> Boolean {

    val fire: (Long) -> Boolean = { t ->
        val period = secondsToPeriod(t)
        val curHours = period.hours
        val curMinutes = period.minutes
        val curSeconds = period.seconds

        if ((curHours > 0) && (hours > 0) && ((curHours % hours) == 0) && (curMinutes == minutes) && (curSeconds == 0)) {
            true
        } else if ((curHours == 0) && (hours == 0) && (curMinutes == minutes) && (curSeconds == 0)) {
            true
        }
        else {
            false
        }
    }
    return fire
}

fun StringBuilder.newLine() {
    this.append(System.lineSeparator())
}

fun Int.toFixedLengthString(len:Int):String {
    return String.format("%0" + "$len" + "d", this)
}

fun dailyAtMidnight() = { time:Long -> ((time % (24 * 60 * 60)) == 0L) }

fun String.removeSingleQuotes():String {
    return PlUtils.removeSingleQuotes(this)
}

fun String.toSequenceDiagramFile(file: File) {
    val reader = SourceStringReader(this)
    reader.generateImage(file)

}

fun Long.toSimulationDateTime(): DateTime {
    val period = Duration(0, this).toPeriod()
    val t0 = DateTime(0, 1, 1, 0, 0, 0, 0)
    val t = t0.plus(period)
    return t
}

fun DateTime.isEqualTo(expected:DateTime) {
    Assertions.assertThat(this).isEqualTo(expected)
}

fun Long.toSimulationDateTimeString():String {
    val t = this.toSimulationDateTime()
    val hours = t.hourOfDay.toFixedLengthString(2)
    val minutes = t.minuteOfHour.toFixedLengthString(2)
    val year = t.year.toFixedLengthString(4)
    val months = t.monthOfYear.toFixedLengthString(2)
    val days = t.dayOfMonth.toFixedLengthString(2)
    return "$year-$months-$days $hours:$minutes"
}

fun String.toPrologTheory(): Prolog {
    val prolog = PlUtils.createEngine()
    PlUtils.loadPrologTheoryAsText(prolog, this)
    return prolog
}

fun Prolog.getResults(query:String, varName:String):List<String> {
    return PlUtils.getResults(this, query, varName)
}

// List<Map<String, String>> getResults(Prolog engine, String query, String[] varNames)

fun Prolog.getResults(query: String, vararg varNames:String):List<Map<String, String>> {
    return PlUtils.getResults(this, query, varNames)
}

fun String?.emptyIfNull():String {
    if (this == null) {
        return ""
    }
    return this
}