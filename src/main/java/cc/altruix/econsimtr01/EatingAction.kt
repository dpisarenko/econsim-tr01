package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class EatingAction : IAction {
    override fun timeToRun(time: Long): Boolean {
        val period = secondsToPeriod(time)
        val hours = period.hours
        val minutes = period.minutes
        val seconds = period.seconds

        if ((hours > 0) && ((hours % 20) == 0) && (minutes == 0) && (seconds == 0)) {
            return true
        }
        return false
    }

    override fun run() {

    }
}
