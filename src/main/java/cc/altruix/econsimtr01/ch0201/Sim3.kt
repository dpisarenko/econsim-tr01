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