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

/**
 * Created by pisarenko on 14.04.2016.
 */
data class Subscriber(val id:String,
                      var interactionsWithStacy:Int = 0,
                      var boughtSomething : Boolean = false)