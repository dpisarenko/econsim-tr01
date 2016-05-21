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
        Mockito.doNothing().`when`(out).appendHeader(builder, out.columns)
        val prolog = Prolog()
        Mockito.doReturn(prolog).`when`(out).createPrologEngine()
        val times = listOf(1L)
        Mockito.doReturn(times).`when`(out).extractTimes(input, prolog)
        Mockito.doNothing().`when`(out).appendRows(builder, prolog, times, out.columns)
        // Run method under test
        val act = out.prologToCsv(input)
        // Verify
        act.shouldBe("Hahaha")
        Mockito.verify(out).createStringBuilder()
        Mockito.verify(out).appendHeader(builder, out.columns)
        Mockito.verify(out).createPrologEngine()
        Mockito.verify(out).extractTimes(input, prolog)
        Mockito.verify(out).appendRows(builder, prolog, times, out.columns)
    }
    @Test
    fun appendRows() {
        val out = Mockito.spy(Sim2TimeSeriesCreator())
        val builder = StringBuilder()
        val prolog = PlUtils.createEngine()
        val times = listOf(0L, 1L)
        val data1 = arrayOf("data1")
        val data2 = arrayOf("data2")
        Mockito.doReturn(data1).`when`(out).calculateData(prolog, 0L, out.columns)
        Mockito.doReturn(data2).`when`(out).calculateData(prolog, 1L, out.columns)
        Mockito.doNothing().`when`(out).appendRow(builder, data1)
        Mockito.doNothing().`when`(out).appendRow(builder, data2)
        // Run method under test
        out.appendRows(builder, prolog, times, out.columns)
        // Verify
        Mockito.verify(out).calculateData(prolog, 0L, out.columns)
        Mockito.verify(out).calculateData(prolog, 1L, out.columns)
        Mockito.verify(out).appendRow(builder, data1)
        Mockito.verify(out).appendRow(builder, data2)
    }
    @Test
    fun appendHeader() {
        val out = Sim2TimeSeriesCreator()
        val builder = StringBuilder()
        // Run method under test
        out.appendHeader(builder, out.columns)
        // Verify
        builder.toString().shouldBe("\"t [min]\";\"Time\";\"Money @ Stacy\";" +
                "\"Copies of software @ Target audience\";\"Total number of " +
                "subscribers in the list\";\"Subscribers (1 interaction)\";\"Subscribers (2 interactions)\";\"Subscribers (3 interactions)\";\"Subscribers (4 interactions)\";\"Subscribers (5 interactions)\";\"Subscribers (6 interactions)\";\"Subscribers (7 or more interactions)\";\n")
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
