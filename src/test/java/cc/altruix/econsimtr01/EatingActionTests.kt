package cc.altruix.econsimtr01

import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class EatingActionTests {
    fun Boolean.shouldBe(expectedValue:Boolean) = Assertions.assertThat(this).isEqualTo(expectedValue)
    fun Boolean.shouldBeTrue() = shouldBe(true)
    fun Boolean.shouldBeFalse() = shouldBe(false)

    @Test
    fun timeToRunSunnyDay() {
        val out = EatingAction(
                Farmer(Mockito.mock(IResourceStorage::class.java), LinkedList(), 30, 1.0),
                Mockito.mock(IResourceStorage::class.java), LinkedList(), 1.0
        )
        out.timeToRun(71999L.secondsToSimulationDateTime()).shouldBeFalse()
        out.timeToRun(72000L.secondsToSimulationDateTime()).shouldBeTrue()
        out.timeToRun(72001L.secondsToSimulationDateTime()).shouldBeFalse()
        out.timeToRun(144000L.secondsToSimulationDateTime()).shouldBeTrue()
        out.timeToRun(216000L.secondsToSimulationDateTime()).shouldBeTrue()
        out.timeToRun(288000L.secondsToSimulationDateTime()).shouldBeTrue()
        out.timeToRun(360000L.secondsToSimulationDateTime()).shouldBeTrue()
        out.timeToRun(432000L.secondsToSimulationDateTime()).shouldBeTrue()
        out.timeToRun(504000L.secondsToSimulationDateTime()).shouldBeTrue()
        out.timeToRun(576000L.secondsToSimulationDateTime()).shouldBeTrue()
        out.timeToRun(647999L.secondsToSimulationDateTime()).shouldBeFalse()
        out.timeToRun(648000L.secondsToSimulationDateTime()).shouldBeTrue()
        out.timeToRun(648001L.secondsToSimulationDateTime()).shouldBeFalse()
    }
}
