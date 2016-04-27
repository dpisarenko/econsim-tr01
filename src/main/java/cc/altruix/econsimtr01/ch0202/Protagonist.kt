package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.PlFlow
import cc.altruix.econsimtr01.ch0201.OncePerWeek
import org.joda.time.DateTime

/**
 * Created by pisarenko on 26.04.2016.
 */
class Protagonist(val offlineNetworkingIntensity:Int,
                  val availableTimePerWeek:Int) : DefaultAgent(ID) {
    companion object {
        val ID = "protagonist"
    }
    init {
        this.addAction(
                PlFlow(
                        id = "prF1",
                        src = "nature",
                        target = ID,
                        resource = Sim1.RESOURCE_AVAILABLE_TIME.id,
                        amount = availableTimePerWeek.toDouble(),
                        timeTriggerFunction = OncePerWeek("Monday")
                )
        )
    }
    /**
     * TODO: Remove these notes
     *
     * Where are we stuck?
     *
     * TODO: Add a flow, which adds X hours of available time to Protagonist
     *
     * How is it done in the previous simulation?
     */
    override fun act(time: DateTime) {
        super.act(time)
    }
}