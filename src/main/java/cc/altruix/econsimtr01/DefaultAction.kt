package cc.altruix.econsimtr01

/**
 * Created by pisarenko on 04.04.2016.
 */
abstract class DefaultAction(val fire: (Long) -> Boolean) : IAction {
    override fun timeToRun(time: Long): Boolean {
        return fire(time)
    }
}