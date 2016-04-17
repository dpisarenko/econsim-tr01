package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.shouldBe
import cc.altruix.javaprologinterop.PlUtils
import org.fest.assertions.Assertions
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
    @Test
    fun appendRows() {
        val out = Mockito.spy(Sim2TimeSeriesCreator())
        val builder = StringBuilder()
        val prolog = PlUtils.createEngine()
        val times = listOf(0L, 1L)
        val data1 = arrayOf<String>("data1")
        val data2 = arrayOf<String>("data2")
        Mockito.doReturn(data1).`when`(out).calculateData(prolog, 0L, Sim2TimeSeriesCreator.columns)
        Mockito.doReturn(data2).`when`(out).calculateData(prolog, 1L, Sim2TimeSeriesCreator.columns)
        Mockito.doNothing().`when`(out).appendRow(builder, data1)
        Mockito.doNothing().`when`(out).appendRow(builder, data2)
        // Run method under test
        out.appendRows(builder, prolog, times, Sim2TimeSeriesCreator.columns)
        // Verify
        Mockito.verify(out).calculateData(prolog, 0L, Sim2TimeSeriesCreator.columns)
        Mockito.verify(out).calculateData(prolog, 1L, Sim2TimeSeriesCreator.columns)
        Mockito.verify(out).appendRow(builder, data1)
        Mockito.verify(out).appendRow(builder, data2)
    }
    @Test
    fun appendHeader() {
        val out = Sim2TimeSeriesCreator()
        val builder = StringBuilder()
        // Run method under test
        out.appendHeader(builder, Sim2TimeSeriesCreator.columns)
        // Verify
        builder.toString().shouldBe("\"t [sec]\";\"Time\";\"Money @ Stacy\";\"Copies of software @ Target audience\";\"Total number of subscribers in the list\";\"Subscribers (1 interaction)\";\"Subscribers (2 interactions)\";\"Subscribers (3 interactions)\";\"Subscribers (4 interactions)\";\"Subscribers (5 interactions)\";\"Subscribers (6 interactions)\";\"Subscribers (7 or more interactions)\";${System.lineSeparator()}")
    }

    @Test
    fun calculateData() {
        val out = Sim2TimeSeriesCreator()
        val prolog = Prolog()
        val columns = arrayOf(
                ColumnDescriptor("c1", {x, y -> "d1"}),
                ColumnDescriptor("c2", {x, y -> "d2"})
        )
        // Run method under test
        val act = out.calculateData(prolog, 0L, columns)
        // Verify
        Assertions.assertThat(act).isEqualTo(arrayOf("d1", "d2"))
    }
}
