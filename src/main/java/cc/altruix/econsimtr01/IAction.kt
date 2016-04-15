package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
interface IAction {
    fun timeToRun(time: DateTime) : Boolean
    fun run(time:DateTime)
    fun notifySubscribers(time:DateTime)
}
