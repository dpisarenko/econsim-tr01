/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
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