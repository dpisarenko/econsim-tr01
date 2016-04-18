package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.shouldBe
import cc.altruix.econsimtr01.simulationRunLogic
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 18.04.2016.
 */
class Sim3Tests {
    @Test
    fun test() {
        // TODO: We model the fact that each new blog post not only activates existing subscribers, but attracts new ones into her list.

        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim3ParametersProvider(
                File("src/test/resources/ch0201Sim3Tests.params.pl").readText()
        )
        val sim = Sim3(
                log,
                flows,
                simParametersProvider
        )
        simulationRunLogic(sim,
                log,
                simParametersProvider.resources,
                flows,
                "src/test/resources/ch0201/sim03/Sim3Tests.test.pl.expected.txt",
                "src/test/resources/ch0201/sim03/Sim3Tests.test.csv.expected.txt",
                "src/test/resources/ch0201/sim03/Sim3Tests.test.flows.actual.png",
                Sim2TimeSeriesCreator()
        )
    }
    @Test
    fun flowF8IsCreated() {
        val simParametersProvider = Sim3ParametersProvider(
                File("src/test/resources/ch0201Sim3Tests.params.pl").readText()
        )
        simParametersProvider.flows.filter { it.id == "f8" }.count().shouldBe(1)
    }
}