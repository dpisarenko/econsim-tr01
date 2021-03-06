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

import cc.altruix.econsimtr01.*
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by pisarenko on 14.04.2016.
 */
class AfterTests {
    @Test
    fun initResetsNextFireTime() {
        val out = After("f1")
        Assertions.assertThat(out.nextFireTime).isNull()
    }

    @Test
    fun updateNextFiringTimeSunnyDay() {
        val out = After("f1")
        val t = 0L.millisToSimulationDateTime()
        out.updateNextFiringTime(t)
        Assertions.assertThat(out.nextFireTime).isEqualTo(t.plusMinutes(1))
    }

    @Test
    fun invokeResetsNextFireTimeWhenItFires() {
        val out = Mockito.spy(After("f1"))
        val t0 = 0L.millisToSimulationDateTime()
        out.nextFireTime = t0.plusMinutes(1)
        out.invoke(t0).shouldBeFalse()
        Assertions.assertThat(out.nextFireTime).isNotNull

        out.invoke(t0.plusMinutes(1)).shouldBeTrue()
        Assertions.assertThat(out.nextFireTime).isNull()
    }
    @Test
    fun connectToInitiatingFunctionFlowSunnyDay() {
        val flow1 = createFlow("f1")
        val flow2 = createFlow("f2")
        val out = After("f1")
        // Run method under test
        out.connectToInitiatingFunctionFlow(mutableListOf(flow1, flow2))
        // Verify
        Mockito.verify(flow1).addFollowUpFlow(out)
        Mockito.verify(flow2, Mockito.never()).addFollowUpFlow(out)
    }

    private fun createFlow(id: String): PlFlow {
        val flow1 = Mockito.spy(
                PlFlow(
                        id,
                        "src",
                        "target",
                        "resource",
                        null,
                        { x -> true }
                )
        )
        return flow1
    }
}