package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 11.04.2016.
 */
class DefaultAgent(val id:String) : IAgent, IResourceStorage {
    val storage:IResourceStorage = DefaultResourceStorage(id)

    override fun act(time: DateTime) {
        throw UnsupportedOperationException()
    }

    override fun id(): String = id

    override fun put(res: String, amt: Double) = storage.put(res, amt)

    override fun amount(res: String): Double = storage.amount(res)

    override fun remove(res: String, amt: Double) = storage.remove(res, amt)

}