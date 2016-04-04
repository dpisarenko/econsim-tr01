package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Farmer(foodStorage: IResourceStorage) : IAgent, IHuman {
    val actions = listOf<IAction>(EatingAction(this, foodStorage))
    var daysWithoutFood : Int = 0
    var alive: Boolean = true
        get
        private set
    companion object {
        const val MAX_DAYS_WITHOUT_FOOD : Int = 30

    }
    override fun eat() {
        this.daysWithoutFood = 0
    }

    override fun hungerOneDay() {
        this.daysWithoutFood++
        if (this.daysWithoutFood > MAX_DAYS_WITHOUT_FOOD) {
            die()
        }
    }

    override fun die() {
        alive = false
    }
    override fun act(time: Long) {
        actions
                .filter { x -> x.timeToRun(time) }
                .forEach { x -> x.run() }
    }
}
