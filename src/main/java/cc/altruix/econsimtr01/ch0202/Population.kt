/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 */

package cc.altruix.econsimtr01.ch0202

import java.util.*

/**
 * Created by pisarenko on 27.04.2016.
 */
class Population(val initialNetworkSize:Int) : IPopulation {
    var people:MutableList<Person> = LinkedList()
        get
        private set

    init {
        for (i in 1..initialNetworkSize) {
            val person = Person()
            person.willingToRecommend = true
            people.add(person)
        }
    }
    override fun people(): MutableList<Person> = this.people

    override fun addPerson(person: Person) {
        people.add(person)
    }
}
