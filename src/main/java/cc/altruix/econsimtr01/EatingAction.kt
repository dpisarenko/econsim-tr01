package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class EatingAction : IAction {
    override fun timeToRun(time: Long): Boolean {
        val hours = secondsToDuration(time).standardHours

        if ((hours > 0) && (hours % 20) == 0L) {
            return true
        }
        return false
    }

    override fun run() {

    }
}
