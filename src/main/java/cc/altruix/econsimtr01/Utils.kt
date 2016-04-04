package cc.altruix.econsimtr01

import org.joda.time.Duration

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
fun secondsToDuration(seconds: Long) = Duration(0, seconds * 1000)

fun secondsToPeriod(seconds: Long) = Duration(0, seconds * 1000).toPeriod()
