/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.DefaultSimulation
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ISensor
import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.ch0202.Sim1ParametersProvider
import org.joda.time.DateTime

/**
 * Created by pisarenko on 17.05.2016.
 */
class BasicAgriculturalSimulation(val logTarget:StringBuilder,
                                  val flows:MutableList<ResourceFlow>,
                                  simParametersProvider:
                                  AgriculturalSimParametersProvider)
: DefaultSimulation(simParametersProvider){
    override fun continueCondition(time: DateTime): Boolean = time.year <= 3

    override fun createAgents(): List<IAgent> = simParametersProvider.agents

    override fun createSensors(): List<ISensor> {
        // TODO: Create measurement of seeds in shack
        // TODO: Create measurement of field area with seeds
        // TODO: Create measurement of empty field area
        // TODO: Create measurement of field are with crop
        throw UnsupportedOperationException()
    }

}