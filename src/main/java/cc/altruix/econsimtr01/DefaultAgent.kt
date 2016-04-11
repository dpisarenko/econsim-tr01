package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 11.04.2016.
 */
class DefaultAgent(val id:String) : IAgent {
    override fun act(time: DateTime) {
        throw UnsupportedOperationException()
    }

    override fun id(): String = id
}