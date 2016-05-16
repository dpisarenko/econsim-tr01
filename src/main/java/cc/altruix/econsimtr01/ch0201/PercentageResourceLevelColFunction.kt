/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.getResults

/**
 * Created by pisarenko on 20.04.2016.
 */
class PercentageResourceLevelColFunction(val agent:String,
                                         val resource:String,
                                         val base:Double) : ITimeSeriesFieldFillerFunction {
    override fun invoke(prolog: Prolog, time: Long): String =
            (prolog.getResults("resourceLevel($time, $agent, '$resource', Amount).", "Amount").first().toDouble() * 100.0 / base).toString()
}