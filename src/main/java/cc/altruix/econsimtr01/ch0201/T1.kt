package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.PlTransformation
import cc.altruix.econsimtr01.findAgent
import org.joda.time.DateTime

/**
 * Created by pisarenko on 22.04.2016.
 */
class T1(id:String,
         agentId:String,
         inputAmount:Double,
         inputResourceId:String,
         outputAmount:Double,
         outputResourceId:String,
         timeTriggerFunction: (DateTime) -> Boolean) :
        PlTransformation(
                id,
                agentId,
                inputAmount,
                inputResourceId,
                outputAmount,
                outputResourceId,
                timeTriggerFunction
        ) {
    override fun timeToRun(time: DateTime): Boolean = timeTriggerFunction(time) &&
            softwareNotComplete()

    private fun softwareNotComplete(): Boolean {
        val stacy = findAgent("stacy", agents)
        if ((stacy != null) && (stacy is DefaultAgent)) {
            return stacy.amount("r14") < 480.0
        }
        return false
    }
}