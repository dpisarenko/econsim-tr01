/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.AbstractAccountant2
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ch0202.SimResRow
import org.joda.time.DateTime

/**
 * Created by pisarenko on 17.05.2016.
 */
class AgriculturalSimulationAccountant(resultsStorage: MutableMap<DateTime,
        SimResRow<AgriculturalSimulationRowField>>,
                                       scenarioName: String) :
        AbstractAccountant2<AgriculturalSimulationRowField>(resultsStorage,
                scenarioName) {
    override fun measure(time: DateTime,
                         agents: List<IAgent>,
                         target:
                         MutableMap<AgriculturalSimulationRowField, Double>) {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }
}