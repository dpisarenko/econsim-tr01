/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.getSubscriberCount

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class SubscribersCohortColFunction(val interactions:Int) : ITimeSeriesFieldFillerFunction {
    override fun invoke(prolog: Prolog, time: Long): String {
        val subscriberResourceLevel = getSubscriberCount(prolog, time, interactions)
        return subscriberResourceLevel.toString()
    }

}

