package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Simulation(val timing : ITiming = Timing()) : ISimulation {
    override fun run() {
        throw UnsupportedOperationException()
    }

}
