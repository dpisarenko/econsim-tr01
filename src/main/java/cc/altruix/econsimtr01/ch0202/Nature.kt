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

/**
 * Created by pisarenko on 11.05.2016.
 */
class Nature : DefaultAgent(ID) {
    companion object {
        val ID = "nature"
    }
    init {
        setInfinite(Sim1ParametersProvider.RESOURCE_AVAILABLE_TIME.id)
    }
}