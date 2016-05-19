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

import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by pisarenko on 19.04.2016.
 */
class PlTransformationTests {
    @Test
    fun timeToRunCallsTimeTriggerFunction() {
        val out = createPlTransformation()
        out.timeToRun(DateTime(2016, 4, 19, 16, 45, 0)).shouldBeTrue()
        out.timeToRun(DateTime(2016, 4, 19, 16, 45, 0).plusMinutes(1)).shouldBeFalse()
        out.timeToRun(DateTime(2016, 4, 19, 16, 45, 0).minusMinutes(1)).shouldBeFalse()
    }

    private fun createPlTransformation(): PlTransformation {
        val out = PlTransformation(
                "id",
                "agent",
                10.0,
                "res1",
                10.0,
                "res2",
                {
                    x ->
                    (x.hourOfDay == 16) && (x.minuteOfHour == 45) && (x.secondOfMinute == 0)
                })
        return out
    }

    @Test
    fun subscriptionWorks() {
        val out = createPlTransformation()
        val subscriber = mock<IActionSubscriber>()
        out.subscribe(subscriber)
        val time = 0L.millisToSimulationDateTime().plusMinutes(123)
        out.notifySubscribers(time)
        Mockito.verify(subscriber).actionOccurred(out, time)
    }

    @Test
    fun run() {
        val out = Mockito.spy(createPlTransformation())
        val agent = mock<DefaultAgent>()
        Mockito.doReturn(agent).`when`(out).findAgent()
        Mockito.`when`(agent.amount("res1")).thenReturn(25.0)
        // Run method under test
        out.run(0L.millisToSimulationDateTime())
        // Verify
        Mockito.verify(out).findAgent()
        Mockito.verify(agent).amount("res1")
        Mockito.verify(agent).remove("res1", 10.0)
        Mockito.verify(agent).put("res2", 10.0)
    }
}