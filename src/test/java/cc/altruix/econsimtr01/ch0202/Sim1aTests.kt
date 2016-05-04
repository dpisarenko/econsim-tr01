package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.millisToSimulationDateTime
import org.fest.assertions.Assertions
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

        val scenarioResults = scenarioDescriptors.map {
            val logTarget = StringBuilder()
            val flows = ArrayList<ResourceFlow>()
            val plFileName = it.name.replace(" ", "_")
            val plPathActual = "src/test/resources/ch0202/sim01a/Sim1aTests$plFileName.pl.actual.txt"
            val plPathExpected = "src/test/resources/ch0202/sim01a/Sim1aTests$plFileName.pl.expected.txt"
            val csvPathActual = "src/test/resources/ch0202/sim01a/Sim1aTests$plFileName.csv.actual.txt"
            val csvPathExpected = "src/test/resources/ch0202/sim01a/Sim1aTests$plFileName.csv.expected.txt"

            val sim = Sim1a(logTarget, flows, it)
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

            val actualPlData = (it.sim as Sim1a).logTarget.toString()
            val plActualFile = File(it.plPathActual)
            plActualFile.writeText(actualPlData)
            val expectedPlData = File(it.plPathExpected).readText()
            Assertions.assertThat(actualPlData).isEqualTo(expectedPlData)

            val actualCsvData = timeSeriesCreator.prologToCsv(plActualFile)
            val csvActualFile = File(it.csvPathActual)
            csvActualFile.writeText(actualCsvData)

            val expectedCsvData = File(it.csvPathExpected).readText()
            Assertions.assertThat(actualCsvData).isEqualTo(expectedCsvData)


        }
    }
}