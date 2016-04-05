package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class EatingAction(val recipient: Farmer,
                   val foodStorage: IResourceStorage,
                   val flows: MutableList<ResourceFlow>,
                   val dailyPotatoConsumption: Double) :
        DefaultAction(composeHourMinuteFiringFunction(20, 0)) {
    override fun run(time:Long) {
        if (recipient.alive) {
            eatIfPossible(time, dailyPotatoConsumption)
        }
    }

    private fun eatIfPossible(time: Long, amount: Double) {
        val potatoes = this.foodStorage.amount(Resource.POTATO)
        if (potatoes < amount) {
            recipient.hungerOneDay()
        } else {
            this.foodStorage.remove(Resource.POTATO, amount)
            this.flows.add(ResourceFlow(time, foodStorage, recipient, Resource.POTATO, amount))
            recipient.eat()
        }
    }
}
