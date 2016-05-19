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

package cc.altruix.econsimtr01

import org.joda.time.DateTime
import java.util.*

/**
 * Created by pisarenko on 11.04.2016.
 */
open class DefaultAgent(val id:String) : IAgent, IResourceStorage {
    val storage:IResourceStorage = DefaultResourceStorage(id)
    val actions = LinkedList<IAction>()
    val resourceLevelObservers = LinkedList<IResourceLevelObserver>()
    override fun act(time: DateTime) {
        val actionsToRun = actions.filter { x -> x.timeToRun(time) }
        actionsToRun.forEach { it.run(time) }
        actionsToRun.forEach { it.notifySubscribers(time) }
        resourceLevelObservers.forEach { it.possibleResourceLevelChange(this, time) }
    }

    open override fun id(): String = id

    override fun put(res: String, amt: Double) = storage.put(res, amt)

    override fun amount(res: String): Double = storage.amount(res)

    override fun remove(res: String, amt: Double) = storage.remove(res, amt)

    override fun setInfinite(res: String) = storage.setInfinite(res)

    override fun isInfinite(res: String): Boolean = storage.isInfinite(res)

    open fun addAction(action:PlFlow) {
        actions.add(action)
    }
    open fun addTransformation(tr:PlTransformation) {
        actions.add(tr)
    }
    open fun addResourceLevelObserver(rlo:IResourceLevelObserver) {
        resourceLevelObservers.add(rlo)
    }
    open override fun init() {
    }
}