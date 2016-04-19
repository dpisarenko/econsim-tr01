package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.simulationRunLogic
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 19.04.2016.
 */
class Sim4Tests {
    @Test
    fun test() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim3ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        val sim = Sim4(
                log,
                flows,
                simParametersProvider
        )
        simulationRunLogic(sim,
                log,
                simParametersProvider.resources,
                flows,
                "src/test/resources/ch0201/sim04/Sim4Tests.test.pl.expected.txt",
                "src/test/resources/ch0201/sim04/Sim4Tests.test.csv.expected.txt",
                "src/test/resources/ch0201/sim04/Sim4Tests.test.flows.actual.png",
                Sim2TimeSeriesCreator()
        )
    }

}