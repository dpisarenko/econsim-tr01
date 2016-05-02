package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
object UuidGenerator {
    private var i = 0;
    fun createUuid():String {
        i++
        return String.format("%06d", i)
    }
}
