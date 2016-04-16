package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.PlFlow
import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
open abstract class ListRelatedFlow (id:String,
                       src: String,
                       target:String,
                       resource:String,
                       amount:Double?,
                       timeTriggerFunction: (DateTime) -> Boolean) :
        PlFlow(id, src, target, resource, amount, timeTriggerFunction) {
    lateinit var list:ListAgent
}
