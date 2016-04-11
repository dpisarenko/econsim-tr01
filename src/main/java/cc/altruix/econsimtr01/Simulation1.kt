package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Simulation1(val logTarget:StringBuilder,
                  val flows:MutableList<ResourceFlow>,
                  val simParametersProvider: SimParametersProvider) : DefaultSimulation(Timing()) {
    val foodStorage = DefaultResourceStorage("FoodStorage")
    val farmer = Farmer(
            foodStorage,
            flows,
            simParametersProvider.maxDaysWithoutFood,
            simParametersProvider.dailyPotatoConsumption
    )

    override fun createSensors(): List<ISensor> =
            listOf(
                    Accountant(
                            foodStorage,
                            farmer,
                            logTarget)
            )

    override fun createAgents(): List<IAgent> {
        foodStorage.put(
                Resource.POTATO.name,
                simParametersProvider.initialAmountOfPotatoes
        )
        val agents = listOf(
                farmer,
                Field(),
                Nature()
        )
        return agents
    }

    override fun continueCondition(tick:Long): Boolean = farmer.alive
}
