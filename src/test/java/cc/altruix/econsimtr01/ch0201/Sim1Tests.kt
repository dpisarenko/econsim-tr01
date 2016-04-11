package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.fest.assertions.Assertions
import org.junit.Ignore
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 08.04.2016.
 */
class Sim1Tests {
    @Test
    @Ignore
    fun test() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()

        val sim = Sim1(
                log,
                flows,
                Sim1ParametersProvider(
                        File("src/test/resources/ch0201Sim1Tests.params.pl").readText()
                )
        )

        // Run method under test
        sim.run()

        // Verify
        val expectedRawSimResultsFile = File("src/test/resources/Simulation1Tests.test.pl.expected.txt")
        val expectedRawSimResults = expectedRawSimResultsFile.readText()
        Assertions.assertThat(log.toString()).isEqualTo(expectedRawSimResults)

        val actualConvertedSimResults = Simulation1TimeSeriesCreator().prologToCsv(expectedRawSimResultsFile)
        val expectedConvertedSimResults = File("src/test/resources/Simulation1Tests.test.csv.expected.txt").readText()
        Assertions.assertThat(actualConvertedSimResults).isEqualTo(expectedConvertedSimResults)

        val seqDiagramTxt = FlowDiagramTextCreator(Collections.emptyList()).createFlowDiagramText(flows)
        seqDiagramTxt.toSequenceDiagramFile(File("src/test/resources/Simulation1Tests.test.flows.actual.png"))

    }
}