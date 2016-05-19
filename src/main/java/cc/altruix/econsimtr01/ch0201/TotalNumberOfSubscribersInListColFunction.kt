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
