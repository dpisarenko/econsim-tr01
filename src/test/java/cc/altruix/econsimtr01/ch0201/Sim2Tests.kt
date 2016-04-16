package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.ResourceFlow
import org.junit.Ignore
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 13.04.2016.
 */
class Sim2Tests {
    @Test
    @Ignore
    fun test() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim1ParametersProvider(
                File("src/test/resources/ch0201Sim1Tests.params.pl").readText()
        )
        /*
        val sim = Sim2(
                log,
                flows,
                simParametersProvider
        )
        */
        /*
        simulationRunLogic(sim,
                log,
                simParametersProvider.resources,
                flows,
                "src/test/resources/ch0201/sim02/Sim2Tests.test.pl.expected.txt",
                "src/test/resources/ch0201/sim02/Sim2Tests.test.csv.expected.txt",
                "src/test/resources/ch0201/sim02/Sim2Tests.test.flows.actual.png"
        )
        */
    }
}
