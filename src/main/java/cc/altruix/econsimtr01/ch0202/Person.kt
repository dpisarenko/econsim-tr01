/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
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
