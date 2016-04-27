package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.DefaultAction
import org.joda.time.DateTime

/**
 * Created by pisarenko on 27.04.2016.
 */
// TODO: Fix this mess
class OfflineNetworkingSession(val offlineNetworkingIntensity:Int) :
        DefaultAction(this)  {
    override fun timeToRun(time: DateTime): Boolean = false
}