package cc.altruix.econsimtr01.ch0201

import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class F2Flow(id:String,
             src: String,
             target:String,
             resource:String,
             amount:Double?,
             timeTriggerFunction: (DateTime) -> Boolean) :
        ListRelatedFlow(id, src, target, resource, amount, timeTriggerFunction) {
    override fun run(time: DateTime) {
        // TODO: Implement this
        // TODO: Test this
        super.run(time)
    }

}
