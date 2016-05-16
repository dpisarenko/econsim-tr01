/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0201

import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
open class F3Flow(id:String,
             src: String,
             target:String,
             resource:String,
             amount:Double?,
             timeTriggerFunction: (DateTime) -> Boolean) :
        ListRelatedFlow(id, src, target, resource, amount, timeTriggerFunction)
{
    override fun run(time: DateTime) {
        this.run(this.list.buyersCount.toDouble(), time)
    }
}
