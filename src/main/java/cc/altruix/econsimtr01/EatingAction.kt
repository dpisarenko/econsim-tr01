package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class EatingAction : DefaultAction(composeHourMinuteFiringFunction(20, 0)) {
    override fun run() {

    }
}
