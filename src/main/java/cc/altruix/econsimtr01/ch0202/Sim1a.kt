package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.ResourceFlow

/**
 * Created by pisarenko on 04.05.2016.
 */
class Sim1a(logTarget:StringBuilder,
            flows:MutableList<ResourceFlow>,
            simParametersProvider: Sim1ParametersProvider) :
        Sim1(logTarget, flows, simParametersProvider) {

}