package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.DefaultAction
import cc.altruix.econsimtr01.IActionSubscriber
import cc.altruix.econsimtr01.IAgent
import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class IntroductionProcess(
        val population:IPopulation,
        triggerFun:(DateTime) -> Boolean) :
        DefaultAction(triggerFun) {
    override fun run(time: DateTime) {
        // TODO: Implement this
        // TODO: Test this
        val network = getNetwork(population)
        val recommenders = getRecommenders(network)
        val leads = recommend(recommenders)

    }
    fun getNetwork(population:IPopulation):List<Person> {
        // TODO: Implement this
        // TODO: Test this
        return emptyList()
    }
    fun getRecommenders(network:List<IAgent>):List<Person> {
        // TODO: Implement this
        // TODO: Test this
        return emptyList()
    }
    fun recommend(recommenders:List<IAgent>):List<Person> {
        // TODO: Implement this
        // TODO: Test this
        return emptyList()
    }
    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }
}
