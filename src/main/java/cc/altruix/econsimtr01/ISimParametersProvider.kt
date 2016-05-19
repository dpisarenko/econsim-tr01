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

import cc.altruix.econsimtr01.ch0201.InfiniteResourceSupply
import cc.altruix.econsimtr01.ch0201.InitialResourceLevel

/**
 * Created by pisarenko on 14.04.2016.
 */
interface ISimParametersProvider {
   val agents:MutableList<IAgent>
        get
    val flows:MutableList<PlFlow>
        get

    val initialResourceLevels:MutableList<InitialResourceLevel>
        get
    val infiniteResourceSupplies:MutableList<InfiniteResourceSupply>
        get
    val transformations:MutableList<PlTransformation>
        get
}