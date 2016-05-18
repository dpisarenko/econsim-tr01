/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.DefaultAgent

/**
 * Created by pisarenko on 16.05.2016.
 */
class Field(val simParamProv:AgriculturalSimParametersProvider)
    : DefaultAgent(ID) {
    companion object {
        val ID = "Field"
    }
    override fun init() {
        put(AgriculturalSimParametersProvider.RESOURCE_EMPTY_AREA.id,
                simParamProv.data["SizeOfField"].toString().toDouble())
        actions.add(Process2(simParamProv))
    }
}
