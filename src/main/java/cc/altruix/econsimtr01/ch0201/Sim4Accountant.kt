/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.AbstractAccountant
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.PlResource

/**
 * Created by pisarenko on 19.04.2016.
 */
class Sim4Accountant(logTarget: StringBuilder,
                     agents: List<IAgent>,
                     resources: List<PlResource>) : AbstractAccountant(logTarget, agents, resources) {
}