/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
        val averageNetworkActivity:Double =
            Sim1ParametersProvider.AVERAGE_NETWORK_ACTIVITY,
        val averageSuggestibilityOfStrangers:Double =
            Sim1ParametersProvider.AVERAGE_SUGGESTIBILITY_OF_STRANGERS
) : DefaultAction(triggerFun) {
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
