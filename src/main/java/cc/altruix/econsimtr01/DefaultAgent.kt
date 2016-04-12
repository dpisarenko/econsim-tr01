package cc.altruix.econsimtr01

import org.joda.time.DateTime
import java.util.*

/**
 * Created by pisarenko on 11.04.2016.
 */
class DefaultAgent(val id:String) : IAgent, IResourceStorage {
    val storage:IResourceStorage = DefaultResourceStorage(id)
    val actions = LinkedList<IAction>()
    override fun act(time: DateTime) {
        actions
                .filter { x -> x.timeToRun(time) }
                .forEach { x -> x.run(time) }

    }

    override fun id(): String = id

    override fun put(res: String, amt: Double) = storage.put(res, amt)

    override fun amount(res: String): Double = storage.amount(res)

    override fun remove(res: String, amt: Double) = storage.remove(res, amt)

    override fun setInfinite(res: String) = storage.setInfinite(res)

    override fun isInfinite(res: String): Boolean = storage.isInfinite(res)

    fun addAction(action:PlFlow) {
        actions.add(action)
    }

}