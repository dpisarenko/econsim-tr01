package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.extractSingleDouble

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class SubscribersCohortColFunction(val interactions:Int) : ITimeSeriesFieldFillerFunction {
    override fun invoke(prolog: Prolog, time: Long): String {
        // TODO: Test this
        // TODO: Implement this

        return prolog.extractSingleDouble("resourceLevel($time, 'list', 'r0${5+interactions}-pc$interactions', Val).", "Val").toString()
    }

}

