package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 20.04.2016.
 */
interface IResourceLevelObserver {
    fun possibleResourceLevelChange(agent:DefaultAgent, time: DateTime)
}