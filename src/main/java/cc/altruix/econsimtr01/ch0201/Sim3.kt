/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.ResourceFlow

/**
 * Created by pisarenko on 18.04.2016.
 */
class Sim3(logTarget:StringBuilder,
           flows:MutableList<ResourceFlow>,
           simParametersProvider: Sim2ParametersProvider) :
        Sim2(logTarget, flows, simParametersProvider) {

}