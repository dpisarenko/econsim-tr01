package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class EatingAction(val recipient: Farmer, val foodStorage: IResourceStorage) :
        DefaultAction(composeHourMinuteFiringFunction(20, 0)) {
    override fun run() {
        if (recipient.alive) {
            val potatoes = this.foodStorage.amount(Resource.POTATO)
            if (potatoes < 1.0) {
                recipient.hungerOneDay()
            } else {
                this.foodStorage.remove(Resource.POTATO, 1.0)
                recipient.eat()
            }
        }
    }
}
