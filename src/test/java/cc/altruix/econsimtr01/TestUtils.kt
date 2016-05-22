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

package cc.altruix.econsimtr01

import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.mockito.Mockito
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 09.04.2016.
 */
fun createDate(t0: DateTime,
                       days:Int,
                       hours:Int,
                       minutes:Int): DateTime{

    return t0.plusDays(days).plusHours(hours).plusMinutes(minutes)
}

fun Boolean.shouldBe(expectedValue:Boolean) = Assertions.assertThat(this).isEqualTo(expectedValue)
fun Boolean.shouldBeTrue() = shouldBe(true)
fun Boolean.shouldBeFalse() = shouldBe(false)

fun String.shouldBe(expectedValue:String) = Assertions.assertThat(this).isEqualTo(expectedValue)
fun Double.shouldBe(expectedValue:Double) = Assertions.assertThat(this).isEqualTo(expectedValue)
fun Int.shouldBe(expectedValue:Int) = Assertions.assertThat(this).isEqualTo(expectedValue)
fun Long.shouldBe(expectedValue:Long) = Assertions.assertThat(this).isEqualTo(expectedValue)

fun Double?.shouldBeNotNull() = Assertions.assertThat(this).isNotNull

inline fun<reified T : Any> mock() = Mockito.mock(T::class.java)

fun simulationRunLogic(sim: ISimulation,
                       log: StringBuilder,
                       resources: List<PlResource>,
                       flows: LinkedList<ResourceFlow>,
                       expectedRawSimResultsFileName: String,
                       actualConvertedSimResultsFileName: String,
                       flowDiagramFileName:
                       String,
                       timeSeriesCreator: ITimeSeriesCreator) {
    // Run method under test
    sim.run()

    // Verify
    val expectedRawSimResultsFile = File(expectedRawSimResultsFileName)
    val expectedRawSimResults = expectedRawSimResultsFile.readText()
    Assertions.assertThat(log.toString()).isEqualTo(expectedRawSimResults)

    val actualConvertedSimResults = timeSeriesCreator.prologToCsv(expectedRawSimResultsFile)
    val expectedConvertedSimResults = File(actualConvertedSimResultsFileName).readText()
    Assertions.assertThat(actualConvertedSimResults).isEqualTo(expectedConvertedSimResults)

    val seqDiagramTxt = FlowDiagramTextCreator(resources).createFlowDiagramText(flows)
    // seqDiagramTxt.toSequenceDiagramFile(File(flowDiagramFileName))
}

fun assertFilesEqual(actualFile:File, expectedFile:File) {
    val actualContents = actualFile.readText()
    val expectedContents = expectedFile.readText()
    Assertions.assertThat(actualContents).isEqualTo(expectedContents)

}

fun mockTimeProvider(): ITimeProvider {
    val timeProvider = mock<ITimeProvider>()
    Mockito.`when`(timeProvider.now()).thenReturn(DateTime(2016, 5, 18,
        6, 26))
    return timeProvider
}
