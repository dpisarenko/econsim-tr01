/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
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
