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
import cc.altruix.econsimtr01.ch03.ValidationResult
import cc.altruix.javaprologinterop.PlUtils
import net.sourceforge.plantuml.SourceStringReader
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.Duration
import org.slf4j.LoggerFactory
import java.io.File
import java.util.*

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
    this.append('\n')
}

fun Int.toFixedLengthString(len:Int):String {
    return String.format("%0" + "$len" + "d", this)
}

fun dailyAtMidnight() = daily(0, 0)

fun daily(hour:Int, minute:Int) = {
    time:DateTime ->
    ((time.hourOfDay == hour) && (time.minuteOfHour == minute) && (time.secondOfMinute == 0))
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

fun DateTime.toDayOfWeekName():String {
    when (this.dayOfWeek) {
        DateTimeConstants.MONDAY -> return "Monday"
        DateTimeConstants.TUESDAY -> return "Tuesday"
        DateTimeConstants.WEDNESDAY -> return "Wednesday"
        DateTimeConstants.THURSDAY -> return "Thursday"
        DateTimeConstants.FRIDAY -> return "Friday"
        DateTimeConstants.SATURDAY -> return "Saturday"
        DateTimeConstants.SUNDAY -> return "Sunday"
    }
    return ""
}

fun Prolog.extractSingleDouble(query: String, varName:String):Double {
    val result = PlUtils.getResults(this, query, varName)?.firstOrNull()
    if (result != null) {
        return result.toDouble()
    }
    LoggerFactory.getLogger(Prolog::class.java).error(
            "Can't find double value. Query: '$query', variable: '$varName"
    )
    return -1.0
}

fun Prolog.extractSingleInt(query: String, varName:String):Int {
    val result = PlUtils.getResults(this, query, varName)?.firstOrNull()
    if (result != null) {
        return result.toInt()
    }
    LoggerFactory.getLogger(Prolog::class.java).error(
            "Can't find int value. Query: '$query', variable: '$varName"
    )
    return -1
}

fun getSubscriberCount(prolog: Prolog, time: Long, interactions: Int): Double {
    val resId = String.format("r%02d-pc%d", (5 + interactions), interactions)
    val subscriberResourceLevel =
            prolog.extractSingleDouble(
                    "resourceLevel($time, 'list', '$resId', Val).",
                    "Val"
            )
    return subscriberResourceLevel
}

fun findAgent(id: String, agentList: List<IAgent>) =
        agentList
                .filter { x -> x.id().equals(id) }
                .firstOrNull()

fun DateTime.isBusinessDay():Boolean = when (this.dayOfWeek) {
    DateTimeConstants.SATURDAY, DateTimeConstants.SUNDAY -> false
    else -> true
}

fun <T>List<T>.extractRandomElements(percentageToExtract:Double,
                                     random:Random):List<T> {
    val elementsCount = (this.size * percentageToExtract).toInt()
    val processedIndices = ArrayList<Int>(elementsCount)
    for (i in 1..elementsCount) {
        var elemIdx = random.nextInt(this.size)
        while (processedIndices.contains(elemIdx)) {
            elemIdx = random.nextInt(this.size)
        }
        processedIndices.add(elemIdx)
    }
    return processedIndices.map { this.get(it) }.toList()
}

// We need to set the seed in order to always get the same random numbers
fun createRandom():java.util.Random =
        java.util.Random(8682522807148012L)

fun randomEventWithProbability(probability:Double) =
        Random.nextDouble() <= probability

fun createCorrectValidationResult():ValidationResult =
        ValidationResult(true, "")
fun createIncorrectValidationResult(msg:String):ValidationResult =
        ValidationResult(false, msg)

fun String.parseDayMonthString() : DayAndMonth {
    val parts = this.split(".")
    return DayAndMonth(parts[0].toInt(), parts[1].toInt())
}

fun DayAndMonth.toDateTime() : DateTime {
    var day = 0L.millisToSimulationDateTime()
    day = day.plusMonths(this.month - 1)
    day = day.plusDays(this.day - 1)
    return day
}

fun calculateBusinessDays(
        start: DayAndMonth,
        end: DayAndMonth
): Int {
    var day = start.toDateTime()
    val end = end.toDateTime()
    var businessDays = 0
    do {
        if ((day.dayOfWeek != DateTimeConstants.SATURDAY) && (day
                .dayOfWeek != DateTimeConstants.SUNDAY)) {
            businessDays++
        }
        day = day.plusDays(1)
    } while (day.isBefore(end) || day.isEqual(end))
    return businessDays
}

fun DayAndMonth.toDateTime(year:Int) = DateTime(year, this.month, this.day,
        0, 0)

fun DateTime.between(start:DayAndMonth, end:DayAndMonth):Boolean {
    val startDateTime = start.toDateTime(this.year)
    val endDateTime = end.toDateTime(this.year)
    return (startDateTime.isBefore(this) || startDateTime.isEqual(this)) &&
            (endDateTime.isAfter(this) || endDateTime.isEqual(this))
}

fun DateTime.evenHourAndMinute(hour:Int, minute:Int):Boolean =
        (this.hourOfDay == hour) && (this.minuteOfHour == minute)
