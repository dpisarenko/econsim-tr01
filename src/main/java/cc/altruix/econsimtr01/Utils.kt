package cc.altruix.econsimtr01

import org.joda.time.Duration

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

        if ((curHours > 0) && ((curHours % hours) == 0) && (curMinutes == minutes) && (curSeconds == 0)) {
            true
        } else {
            false
        }
    }
    return fire
}