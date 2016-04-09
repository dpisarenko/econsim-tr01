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

fun composeHourMinuteFiringFunction(hours:Int, minutes:Int): (DateTime) -> Boolean {

    val fire: (DateTime) -> Boolean = { t ->
        val curHours = t.hourOfDay
        val curMinutes = t.minuteOfHour
        val curSeconds = t.secondOfMinute

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

fun dailyAtMidnight() = {
    time:DateTime ->
    ((time.hourOfDay == 0) && (time.minuteOfDay == 0))
}

fun String.removeSingleQuotes():String {
    return PlUtils.removeSingleQuotes(this)
}

fun String.toSequenceDiagramFile(file: File) {
    val reader = SourceStringReader(this)
    reader.generateImage(file)

}

fun Long.millisToSimulationDateTime(): DateTime {
    val period = Duration(0, this).toPeriod()
    val t0 = t0()
    val t = t0.plus(period)
    return t
}

fun Long.secondsToSimulationDateTime(): DateTime = (this * 1000L).millisToSimulationDateTime()


fun t0() = DateTime(0, 1, 1, 0, 0, 0, 0)

fun DateTime.isEqualTo(expected:DateTime) {
    Assertions.assertThat(this).isEqualTo(expected)
}

fun Long.toSimulationDateTimeString():String =
    this.millisToSimulationDateTime().toSimulationDateTimeString()

fun DateTime.toSimulationDateTimeString():String {
    val hours = this.hourOfDay.toFixedLengthString(2)
    val minutes = this.minuteOfHour.toFixedLengthString(2)
    val year = this.year.toFixedLengthString(4)
    val months = this.monthOfYear.toFixedLengthString(2)
    val days = this.dayOfMonth.toFixedLengthString(2)
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

fun DateTime.millisSinceT0():Long {
    return this.minus(t0().millis).millis
}
fun DateTime.secondsSinceT0():Long {
    return this.millisSinceT0()/1000L
}
