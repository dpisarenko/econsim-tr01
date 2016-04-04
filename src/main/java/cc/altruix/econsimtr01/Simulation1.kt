package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Simulation1() : DefaultSimulation(Timing()) {
    val foodStorage = DefaultResourceStorage()
    val farmer = Farmer(foodStorage)

    override fun createSensors(): List<ISensor> = listOf(Accountant(foodStorage, farmer))

    override fun createAgents(): List<IAgent> {
        foodStorage.put(Resource.POTATO, 30*3.0)
        val agents = listOf(
                farmer,
                Field(),
                Nature()
        )
        return agents
    }

    override fun continueCondition(): Boolean = farmer.alive

}
