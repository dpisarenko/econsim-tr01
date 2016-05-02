package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
object Random : IRandom {
    val random = createRandom()
    override fun nextInt(bound: Int): Int = random.nextInt(bound)
}
