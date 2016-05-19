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

import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.UuidGenerator

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Person : DefaultAgent("person-${UuidGenerator.createUuid()}") {
    var willingToMeet: Boolean = false
        get
        set
    var willingToRecommend: Boolean = false
        get
        set
    var offlineNetworkingSessionHeld:Boolean = false
        get
        set
    var willingToPurchase:Boolean = false
        get
        set
}
