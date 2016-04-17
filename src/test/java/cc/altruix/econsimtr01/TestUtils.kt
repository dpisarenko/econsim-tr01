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
