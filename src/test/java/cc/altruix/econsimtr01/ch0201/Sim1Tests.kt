/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.simulationRunLogic
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 08.04.2016.
 */
class Sim1Tests {
    @Test
    //@Ignore
    fun test() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim1ParametersProvider(
                File("src/test/resources/ch0201Sim1Tests.params.pl").readText()
        )
        val sim = Sim1(
                log,
                flows,
                simParametersProvider
        )
        simulationRunLogic(sim,
                log,
                simParametersProvider.resources,
                flows,
                "src/test/resources/ch0201/Sim1Tests.test.pl.expected.txt",
                "src/test/resources/ch0201/Sim1Tests.test.csv.expected.txt",
                "src/test/resources/ch0201/Sim1Tests.test.flows.actual.png",
                Sim1TimeSeriesCreator()
        )
    }
}
