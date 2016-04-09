package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 09.04.2016.
 */
fun createDate(t0: DateTime,
                       days:Int,
                       hours:Int,
                       minutes:Int): DateTime{

    return t0.plusDays(days).plusHours(hours).plusMinutes(minutes)
}

