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

package cc.altruix.econsimtr01

import cc.altruix.econsimtr01.ch0201.InfiniteResourceSupply
import cc.altruix.econsimtr01.ch0201.InitialResourceLevel
import org.fest.assertions.Assertions
import org.junit.Test
import java.io.File
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Simulation1Tests {
    @Test
    fun test() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()

        val sim = Simulation1(
                log,
                flows,
                SimParametersProvider(
                        File("src/test/resources/Simulation1.params.pl").readText(),
                        Collections.emptyList<IAgent>(),
                        Collections.emptyList<PlFlow>(),
                        Collections.emptyList<InitialResourceLevel>(),
                        Collections.emptyList<InfiniteResourceSupply>()
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
