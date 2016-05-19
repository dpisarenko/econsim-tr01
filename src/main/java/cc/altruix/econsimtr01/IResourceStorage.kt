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

package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
interface IResourceStorage : ISometingIdentifiable {

    fun put(res: String, amt:Double)
    fun amount(res: String):Double

    fun remove(res: String, amt: Double)
    fun setInfinite(res:String)
    fun isInfinite(res:String):Boolean
}
