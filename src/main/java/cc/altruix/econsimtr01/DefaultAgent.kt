package cc.altruix.econsimtr01

import cc.altruix.econsimtr01.ch0201.After
import org.joda.time.DateTime
import java.util.*

/**
 * Created by pisarenko on 11.04.2016.
 */
open class DefaultAgent(val id:String) : IAgent, IResourceStorage {
    val storage:IResourceStorage = DefaultResourceStorage(id)
    val actions = LinkedList<IAction>()
    val transformations = LinkedList<PlTransformation>()
    override fun act(time: DateTime) {
        val actionsToRun = actions.filter { x -> x.timeToRun(time) }
        actionsToRun.forEach { it.run(time) }
        actionsToRun.forEach { it.notifySubscribers(time) }
        // TODO: Implement execution of transformations here
        // TODO: Test execution of transformations here
    }

    override fun id(): String = id

    override fun put(res: String, amt: Double) = storage.put(res, amt)

    override fun amount(res: String): Double = storage.amount(res)

    override fun remove(res: String, amt: Double) = storage.remove(res, amt)

    override fun setInfinite(res: String) = storage.setInfinite(res)

    override fun isInfinite(res: String): Boolean = storage.isInfinite(res)

    open fun addAction(action:PlFlow) {
        actions.add(action)
    }
    open fun addTransformation(tr:PlTransformation) {
        // TODO: Test this
        transformations.add(tr)
    }
}