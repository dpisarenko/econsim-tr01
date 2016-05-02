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
        // TODO: Implement this
        // TODO: Test this
        for (i in 1..initialNetworkSize) {

        }
    }
}
