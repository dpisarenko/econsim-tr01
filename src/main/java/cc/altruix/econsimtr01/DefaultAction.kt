package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 04.04.2016.
 */
abstract class DefaultAction(val fire: (DateTime) -> Boolean) : IAction {
    override fun timeToRun(time: DateTime): Boolean {
        return fire(time)
    }
}