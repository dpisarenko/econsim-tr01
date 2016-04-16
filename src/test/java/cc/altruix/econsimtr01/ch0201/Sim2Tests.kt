package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.simulationRunLogic
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 13.04.2016.
 */
class Sim2Tests {
    @Test
    fun test() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim2ParametersProvider(
                File("src/test/resources/ch0201Sim2Tests.params.pl").readText()
        )
        val sim = Sim2(
                log,
                flows,
                simParametersProvider
        )
        simulationRunLogic(sim,
                log,
                simParametersProvider.resources,
                flows,
                "src/test/resources/ch0201/sim02/Sim2Tests.test.pl.expected.txt",
                "src/test/resources/ch0201/sim02/Sim2Tests.test.csv.expected.txt",
                "src/test/resources/ch0201/sim02/Sim2Tests.test.flows.actual.png"
        )
    }
}
