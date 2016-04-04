package cc.altruix.econsimtr01

import org.joda.time.Duration

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Timing : ITiming {
    var ticks : Long = 0 // seconds

    override fun gotFuture(): Boolean =
            ticks < 2 * 365 * 24 * 60 * 60

    override fun tick():Long {
        ticks += 60 // We are going minute by minute
        return ticks
    }

    fun now():Duration = secondsToDuration(ticks)
}
