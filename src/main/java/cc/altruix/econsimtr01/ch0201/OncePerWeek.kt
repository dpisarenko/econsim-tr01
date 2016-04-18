package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.secondsSinceT0
import cc.altruix.econsimtr01.toDayOfWeekName
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 14.04.2016.
 */
class OncePerWeek(val dayOfWeek:String) : (DateTime) -> Boolean {
    val LOGGER = LoggerFactory.getLogger(OncePerWeek::class.java)
    override fun invoke(t: DateTime): Boolean {
//        LOGGER.debug("t: ${t.secondsSinceT0()} / $t, dayOfWeek: ${t.dayOfWeek}, toDayOfWeekName: ${t.toDayOfWeekName()}, result: ${dayOfWeek.equals(t.toDayOfWeekName())}")
        return dayOfWeek.equals(t.toDayOfWeekName()) && (t.hourOfDay == 0) && (t.minuteOfHour == 0) &&
                (t.secondOfMinute == 0)
    }
}
