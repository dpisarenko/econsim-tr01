package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Simulation1(val timing : ITiming = Timing()) : ISimulation {
    override fun run():SimResults {
        val results = SimResults()
        val foodStorage = DefaultResourceStorage()
        foodStorage.put(Resource.POTATO, 30*3.0)
        val farmer = Farmer(foodStorage)
        val agents = listOf(
                farmer,
                Field(),
                Nature()
        )
        val sensors = listOf(Accountant(foodStorage, farmer))
        while (timing.gotFuture() && farmer.alive) {
            val time = timing.tick()
            agents.forEach { x -> x.act(time) }
            sensors.forEach { x -> x.measure(time) }
        }
        sensors.forEach { x -> x.finito() }
        return results
    }
}
