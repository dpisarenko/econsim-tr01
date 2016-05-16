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
class TotalNumberOfSubscribersInListColFunction : ITimeSeriesFieldFillerFunction {
    override fun invoke(prolog: Prolog, time: Long): String =
            arrayOf(1, 2, 3, 4, 5, 6, 7).map {
                getSubscriberCount(prolog, time, it)
            }.sum().toString()
}
