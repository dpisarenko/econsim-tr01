package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
open class IntroductionProcess(
        val population:IPopulation,
        triggerFun:(DateTime) -> Boolean,
        val averageNetworkActivity:Double = Sim1.AVERAGE_NETWORK_ACTIVITY) :
        DefaultAction(triggerFun) {
    open override fun run(time: DateTime) {
        val network = getNetwork(population)
        val recommenders = getRecommenders(network)
        val leads = recommend(recommenders)
        leads.forEach { it.willingToMeet = true }
    }
    open fun getNetwork(population:IPopulation):List<Person> =
            population
                    .people()
                    .filter { it.willingToRecommend }
                    .toList()

    // TODO: Test this
    open fun getRecommenders(network:List<Person>):List<Person> =
            network.extractRandomElements(averageNetworkActivity, Random)

    open fun recommend(recommenders:List<IAgent>):List<Person> {
        // TODO: Implement this
        // TODO: Test this
        return emptyList()
    }
    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }
}
