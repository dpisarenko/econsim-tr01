/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
object Random : IRandom {
    val random = createRandom()

    override fun nextInt(bound: Int): Int = random.nextInt(bound)
    override fun nextDouble(): Double = random.nextDouble()
}
