package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.PlFlow
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
             timeTriggerFunction: (DateTime) -> Boolean,
             val list:ListAgent) :
        PlFlow(id, src, target, resource, amount, timeTriggerFunction) {
}
