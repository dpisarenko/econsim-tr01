package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.DefaultAction
import cc.altruix.econsimtr01.IActionSubscriber
import cc.altruix.econsimtr01.Random
import cc.altruix.econsimtr01.extractRandomElements
import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
open class IntroductionProcess(
        val population:IPopulation,
        triggerFun:(DateTime) -> Boolean,
        val averageNetworkActivity:Double = Sim1.AVERAGE_NETWORK_ACTIVITY,
        val averageSuggestibilityOfStrangers:Double = Sim1.AVERAGE_SUGGESTIBILITY_OF_STRANGERS) :
        DefaultAction(triggerFun) {
    open override fun run(time: DateTime) {
        val network = getNetwork(population)
        val recommenders = getRecommenders(network)
        val successfulRecommenders = recommend(recommenders)
        successfulRecommenders.forEach {
            val person = createPersonWillingToMeet()
            population.addPerson(person)
        }
    }

    open fun createPersonWillingToMeet(): Person {
        val person = Person()
        person.willingToMeet = true
        return person
    }

    open fun getNetwork(population:IPopulation):List<Person> =
            population
                    .people()
                    .filter { it.willingToRecommend }
                    .toList()

    open fun getRecommenders(network:List<Person>):List<Person> =
            network.extractRandomElements(averageNetworkActivity, Random)

    open fun recommend(recommenders:List<Person>):List<Person> =
            recommenders.extractRandomElements(averageSuggestibilityOfStrangers, Random)

    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }
}
