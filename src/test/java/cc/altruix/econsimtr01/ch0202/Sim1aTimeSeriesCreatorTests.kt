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

import cc.altruix.econsimtr01.millisToSimulationDateTime
import cc.altruix.econsimtr01.mock
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * Created by pisarenko on 06.05.2016.
 */
class Sim1aTimeSeriesCreatorTests {
    @Test
    fun runWiring() {
        // Prepare
        val simData = HashMap<DateTime, SimResRow<Sim1aResultRowField>>()
        val t0 = 0L.millisToSimulationDateTime()
        val row0 = SimResRow<Sim1aResultRowField>(t0)
        val t1 = t0.plusMinutes(1)
        val row1 = SimResRow<Sim1aResultRowField>(t1)
        simData.put(t0, row0)
        simData.put(t0, row1)
        val targetFileName = "targetFileName"
        val out = Mockito.spy(Sim1aTimeSeriesCreator(simData, targetFileName, emptyList()))
        val times = arrayListOf(t0, t1)
        val builder = StringBuilder()
        Mockito.doReturn(builder).`when`(out).createStringBuilder()
        Mockito.doReturn(times).`when`(out).toMutableList()
        Mockito.doNothing().`when`(out).sort(times)
        val header = "header"
        Mockito.doReturn(header).`when`(out).composeHeader()
        val rowString0 = "rowString0"
        val rowString1 = "rowString1"
        Mockito.doReturn(rowString0).`when`(out).composeRowData(t0)
        Mockito.doReturn(rowString1).`when`(out).composeRowData(t1)
        Mockito.doNothing().`when`(out).writeToFile(builder)
        // Run method under test
        out.run()
        // Verify
        Mockito.verify(out).toMutableList()
        Mockito.verify(out).sort(times)
        Mockito.verify(out).createStringBuilder()
        Mockito.verify(out).composeRowData(t0)
        Mockito.verify(out).composeRowData(t1)
        Mockito.verify(out).writeToFile(builder)
        Assertions.assertThat(builder.toString()).isEqualTo(header + rowString0 + rowString1)
    }
    @Test
    fun composeHeader() {
        // Prepare
        val simData = HashMap<DateTime, SimResRow<Sim1aResultRowField>>()
        val targetFileName = "targetFileName"
        val sim1Name = "Scenario 1"
        val sim2Name = "Scenario 2"
        val out = Sim1aTimeSeriesCreator(
                simData,
                targetFileName,
                listOf(sim1Name, sim2Name)
        )
        // Run method under test
        val header = out.composeHeader()
        // Verify
        Assertions.assertThat(header).isEqualTo(
"""t;"Scenario 1: Number of people willing to meet";"Scenario 1: Number of people willing to recommend my friend";"Scenario 1: Number of people my friend had an offline networking session with";"Scenario 1: Number of people willing to purchase my friend's services, if need arises";"Scenario 2: Number of people willing to meet";"Scenario 2: Number of people willing to recommend my friend";"Scenario 2: Number of people my friend had an offline networking session with";"Scenario 2: Number of people willing to purchase my friend's services, if need arises";
"""
        );
    }
    @Test
    fun composeRowDataDefaultScenario() {
        // Prepare
        val simData = HashMap<DateTime, SimResRow<Sim1aResultRowField>>()
        val targetFileName = "targetFileName"
        val sim1Name = "Scenario 1"
        val sim2Name = "Scenario 2"
        val out = Sim1aTimeSeriesCreator(
                simData,
                targetFileName,
                listOf(sim1Name, sim2Name)
        )
        val t = 0L.millisToSimulationDateTime()
        val row = SimResRow<Sim1aResultRowField>(t)
        row.data.put(sim1Name, hashMapOf(
                Pair(
                        Sim1aResultRowField.PEOPLE_MET,
                        1.0
                ),
                Pair(
                        Sim1aResultRowField.PEOPLE_WILLING_TO_MEET,
                        2.0
                ),
                Pair(
                        Sim1aResultRowField.PEOPLE_WILLING_TO_PURCHASE,
                        3.0
                ),
                Pair(
                        Sim1aResultRowField.PEOPLE_WILLING_TO_RECOMMEND,
                        4.0
                )
        ))
        row.data.put(sim2Name,
                hashMapOf(
                Pair(
                        Sim1aResultRowField.PEOPLE_MET,
                        5.0
                ),
                Pair(
                        Sim1aResultRowField.PEOPLE_WILLING_TO_MEET,
                        6.0
                ),
                Pair(
                        Sim1aResultRowField.PEOPLE_WILLING_TO_PURCHASE,
                        7.0
                ),
                Pair(
                        Sim1aResultRowField.PEOPLE_WILLING_TO_RECOMMEND,
                        8.0
                )
        ))
        simData.put(t, row)
        // Run method under test
        val actRes = out.composeRowData(t)
        // Verify
        Assertions.assertThat(actRes).isEqualTo("""0000-01-01 00:00;"2.0";"4.0";"1.0";"3.0";"6.0";"8.0";"5.0";"7.0";
""")
    }
}