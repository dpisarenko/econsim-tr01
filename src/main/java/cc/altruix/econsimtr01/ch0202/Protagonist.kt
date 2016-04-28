package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.PlFlow
import cc.altruix.econsimtr01.ch0201.OncePerWeek
import cc.altruix.econsimtr01.dailyAtMidnight
import org.joda.time.DateTime

/**
 * Created by pisarenko on 26.04.2016.
 */
class Protagonist(val offlineNetworkingIntensity:Int,
                  val availableTimePerWeek:Int,
                  val maxNetworkingSessionsPerBusinessDay:Int) : DefaultAgent(ID) {
    companion object {
        val ID = "protagonist"
    }
    init {
        val mondayMidnight = OncePerWeek("Monday")
        this.addAction(
                PlFlow(
                        id = "prF1",
                        src = "nature",
                        target = ID,
                        resource = Sim1.RESOURCE_AVAILABLE_TIME.id,
                        amount = availableTimePerWeek.toDouble(),
                        timeTriggerFunction = mondayMidnight
                )
        )
        this.actions.add(
                OfflineNetworkingSessionsHeldDuringWeekResetAction(
                        this,
                        mondayMidnight
                )
        )
        this.actions.add(
                OfflineNetworkingSessionsHeldDuringDayResetAction(
                        this,
                        dailyAtMidnight()
                )
        )
        this.actions.add(
                OfflineNetworkingSession(
                        this,
                        offlineNetworkingIntensity,
                        maxNetworkingSessionsPerBusinessDay
                )
        )

    }
    /**
     * TODO: Remove these notes
     *
     * Where are we stuck?
     * TODO: Add networking meeting
     */
    var offlineNetworkingSessionsHeldDuringCurrentWeek:Int = 0

    var offlineNetworkingSessionsHeldDuringCurrentDay:Int = 0

    override fun act(now: DateTime) {
        super.act(now)
    }
}