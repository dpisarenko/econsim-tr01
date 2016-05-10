package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.ResourceFlow
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
        val sim1Name = "Scenario 1"
        val sim2Name = "Scenario 2"
        val scenarioDescriptors = listOf(
                Sim1ParametersProvider(
                    name = sim1Name,
                    agents = ArrayList(),
                    flows = ArrayList(),
                    initialResourceLevels = ArrayList(),
                    infiniteResourceSupplies = ArrayList(),
                    transformations = ArrayList(),
                    initialNetworkSize = 100
                ),
                Sim1ParametersProvider(
                    name = sim2Name,
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
            Sim1a(
                    logTarget = StringBuilder(),
                    flows = ArrayList<ResourceFlow>(),
                    simParametersProvider = it,
                    resultsStorage = simResults
            )
        }
        scenarioResults.forEach {
            it.run()
        }
        val actualFileName = "src/test/resources/ch0202/sim01a/Sim1aTests.test.actual.csv"

        val timeSeriesCreator = Sim1aTimeSeriesCreator(
                simResults,
                actualFileName,
                listOf(sim1Name, sim2Name)
        )
        timeSeriesCreator.run()
        val expectedFileName = "src/test/resources/ch0202/sim01a/Sim1aTests.test.actual.csv"
        val expectedContents = File(expectedFileName).readText()
        val actualContents = File(actualFileName).readText()
        Assertions.assertThat(actualContents).isEqualTo(expectedContents)

        // TODO: Make this test run
    }
    @Test
    fun createSensors() {
        // Prepare
        val logTarget = StringBuilder()
        val flows = ArrayList<ResourceFlow>()
        val simResults = HashMap<DateTime,Sim1aResultsRow>()
        val paramsProvider = Sim1ParametersProvider(
                name = "Scenario 1",
                agents = ArrayList(),
                flows = ArrayList(),
                initialResourceLevels = ArrayList(),
                infiniteResourceSupplies = ArrayList(),
                transformations = ArrayList(),
                initialNetworkSize = 100
        )
        val sim = Sim1a(logTarget, flows, paramsProvider, simResults)
        // Run method under test
        val sensors = sim.createSensors()
        // Verify
        Assertions.assertThat(sensors).isNotNull
        Assertions.assertThat(sensors.size).isEqualTo(1)
        Assertions.assertThat(sensors.get(0) is Sim1aAccountant).isTrue()
    }
}
