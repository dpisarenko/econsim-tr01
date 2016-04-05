package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Farmer(foodStorage: IResourceStorage,
             flows: MutableList<ResourceFlow>,
             val maxDaysWithoutFood: Int,
             dailyPotatoConsumption: Double) : IAgent, IHuman {
    override fun id(): String = "Farmer"

    val actions = listOf<IAction>(
            EatingAction(
                this,
                foodStorage,
                flows,
                dailyPotatoConsumption
            )
    )
    var daysWithoutFood : Int = 0
    var alive: Boolean = true
        get
        private set
    override fun eat() {
        this.daysWithoutFood = 0
    }

    override fun hungerOneDay() {
        this.daysWithoutFood++
        if (this.daysWithoutFood > maxDaysWithoutFood) {
            die()
        }
    }

    override fun die() {
        alive = false
    }
    override fun act(time: Long) {
        actions
                .filter { x -> x.timeToRun(time) }
                .forEach { x -> x.run(time) }
    }
}
