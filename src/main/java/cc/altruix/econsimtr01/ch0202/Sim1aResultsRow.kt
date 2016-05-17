/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0202

import org.joda.time.DateTime
import java.util.*

/**
 * Created by pisarenko on 04.05.2016.
 * Sim1aResultRowField
 */
class Sim1aResultsRow<T>(val time: DateTime) {
    val data:MutableMap<String,MutableMap<T,Double>> = HashMap()
}