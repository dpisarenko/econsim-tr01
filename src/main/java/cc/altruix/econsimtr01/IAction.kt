package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
interface IAction {
    fun timeToRun(time:Long) : Boolean
    fun run(time:Long)
}
