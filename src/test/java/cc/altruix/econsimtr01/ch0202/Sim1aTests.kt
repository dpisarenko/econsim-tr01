package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.ResourceFlow
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test
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
        val expectedFileName = "src/test/resources/ch0202/sim01a/Sim1aTests.test.actual.csv"
        val timeSeriesCreator = Sim1aTimeSeriesCreator(
                simResults,
                actualFileName
        )
        timeSeriesCreator.run()
        // TODO: Continue here
        // TODO: Write out the data from simResults to actualFileName
        // TODO: Compare contents of actualFileName with expectedFileName (should be equal)
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
