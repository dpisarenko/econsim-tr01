/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.ResourceFlow
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Ignore
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 04.05.2016.
 */
class Sim1aTests {
    @Test
    @Ignore
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
        val simResults = HashMap<DateTime, SimResRow<Sim1aResultRowField>>()
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
    }
    @Test
    fun createSensors() {
        // Prepare
        val logTarget = StringBuilder()
        val flows = ArrayList<ResourceFlow>()
        val simResults = HashMap<DateTime, SimResRow<Sim1aResultRowField>>()
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
