package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.shouldBe
import org.junit.Test
import org.mockito.Mockito
import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Sim2TimeSeriesCreatorTests {
    @Test
    fun prologToCsvSunnyDay() {
        val out = Mockito.spy(Sim2TimeSeriesCreator())
        val input = File("someFile")
        val builder = StringBuilder("Hahaha")
        Mockito.doReturn(builder).`when`(out).createStringBuilder()
        Mockito.doNothing().`when`(out).appendHeader(builder, Sim2TimeSeriesCreator.columns)
        val prolog = Prolog()
        Mockito.doReturn(prolog).`when`(out).createPrologEngine()
        val times = listOf(1L)
        Mockito.doReturn(times).`when`(out).extractTimes(input, prolog)
        Mockito.doNothing().`when`(out).appendRows(builder, prolog, times, Sim2TimeSeriesCreator.columns)
        // Run method under test
        val act = out.prologToCsv(input)
        // Verify
        act.shouldBe("Hahaha")
        Mockito.verify(out).createStringBuilder()
        Mockito.verify(out).appendHeader(builder, Sim2TimeSeriesCreator.columns)
        Mockito.verify(out).createPrologEngine()
        Mockito.verify(out).extractTimes(input, prolog)
        Mockito.verify(out).appendRows(builder, prolog, times, Sim2TimeSeriesCreator.columns)
    }
}
