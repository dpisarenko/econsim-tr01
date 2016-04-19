package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.shouldBeFalse
import cc.altruix.econsimtr01.shouldBeTrue
import cc.altruix.econsimtr01.simulationRunLogic
import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 19.04.2016.
 */
class Sim4Tests {
    @Test
    fun test() {
        // TODO: Implement reaction to hasTransformation
        // TODO: Implement whenResourceReachesLevel

        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim4ParametersProvider(
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
                Sim4TimeSeriesCreator()
        )
    }
    @Test
    fun continueCondition() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        val out = Sim4(
                log,
                flows,
                simParametersProvider
        )
        out.continueCondition(DateTime(0, 1, 1, 0, 0, 0)).shouldBeTrue()
        out.continueCondition(DateTime(1, 1, 1, 0, 0, 0)).shouldBeFalse()
    }
    @Test
    fun createAgentsCallsAttachTransformationsToAgents() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        val out = Mockito.spy(
                Sim4(
                        log,
                        flows,
                        simParametersProvider
                )
        )
        Mockito.doNothing().`when`(out).attachFlowsToAgents(simParametersProvider.flows,
                simParametersProvider.agents,
                flows)
        Mockito.doNothing().`when`(out).attachTransformationsToAgents(
                simParametersProvider.transformations,
                simParametersProvider.agents
        )
        Mockito.doNothing().`when`(out).setInitialResourceLevels()
        Mockito.doNothing().`when`(out).setInfiniteResourceSupplies()
        // Run method under test
        out.createAgents()
        // Verify
        Mockito.verify(out).attachFlowsToAgents(simParametersProvider.flows,
                simParametersProvider.agents,
                flows)
        Mockito.verify(out).attachTransformationsToAgents(
                simParametersProvider.transformations,
                simParametersProvider.agents
        )
        Mockito.verify(out).setInitialResourceLevels()
        Mockito.verify(out).setInfiniteResourceSupplies()
    }
}