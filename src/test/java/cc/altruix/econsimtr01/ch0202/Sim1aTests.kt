package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.millisToSimulationDateTime
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 04.05.2016.
 */
class Sim1aTests {
    @Test
    fun test() {
        val scenarioDescriptors = listOf(
                Sim1ParametersProvider(
                    name = "Scenario 1",
                    agents = ArrayList(),
                    flows = ArrayList(),
                    initialResourceLevels = ArrayList(),
                    infiniteResourceSupplies = ArrayList(),
                    transformations = ArrayList(),
                    initialNetworkSize = 100
                ),
                Sim1ParametersProvider(
                    name = "Scenario 2",
                    agents = ArrayList(),
                    flows = ArrayList(),
                    initialResourceLevels = ArrayList(),
                    infiniteResourceSupplies = ArrayList(),
                    transformations = ArrayList(),
                    initialNetworkSize = 1000
                )
        )
        val simResults = HashMap<DateTime,Sim1aResultsRow>()
        val scenarioResults = scenarioDescriptors.map {
            // TODO: Remove (if you can) logTarget
            val logTarget = StringBuilder()
            // TODO: Remove (if you can) flows
            val flows = ArrayList<ResourceFlow>()
            // TODO: Remove plFileName
            val plFileName = it.name.replace(" ", "_")
            // TODO: Remove plPathActual
            val plPathActual = "src/test/resources/ch0202/sim01a/Sim1aTests$plFileName.pl.actual.txt"
            // TODO: Remove plPathExpected
            val plPathExpected = "src/test/resources/ch0202/sim01a/Sim1aTests$plFileName.pl.expected.txt"
            // TODO: Remove csvPathActual
            val csvPathActual = "src/test/resources/ch0202/sim01a/Sim1aTests$plFileName.csv.actual.txt"
            // TODO: Remove csvPathExpected
            val csvPathExpected = "src/test/resources/ch0202/sim01a/Sim1aTests$plFileName.csv.expected.txt"

            val sim = Sim1a(logTarget, flows, it, simResults)
            ScenarioResultsTuple(
                    sim,
                    plPathActual,
                    plPathExpected,
                    csvPathActual,
                    csvPathExpected
            )
        }
        val timeSeriesCreator = Sim1aTimeSeriesCreator()
        scenarioResults.forEach {
            it.sim.run()
        }

        val actualFileName = "src/test/resources/ch0202/sim01a/Sim1aTests.test.actual.csv"
        val expectedFileName = "src/test/resources/ch0202/sim01a/Sim1aTests.test.actual.csv"
        // TODO: Continue here
        // TODO: Write out the data from simResults to actualFileName
        // TODO: Compare contents of actualFileName with expectedFileName (should be equal)
    }
}