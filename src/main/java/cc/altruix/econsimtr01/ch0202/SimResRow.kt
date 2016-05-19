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

package cc.altruix.econsimtr01.ch0202

import org.joda.time.DateTime
import java.util.*

/**
 * Created by pisarenko on 04.05.2016.
 * Sim1aResultRowField
 */
class SimResRow<T>(val time: DateTime) {
    val data:MutableMap<String,MutableMap<T,Double>> = HashMap()
}