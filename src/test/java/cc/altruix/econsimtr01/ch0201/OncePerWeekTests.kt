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
import org.joda.time.DateTime
import org.junit.Test

/**
 * Created by pisarenko on 14.04.2016.
 */
class OncePerWeekTests {
    @Test
    fun sunnyDay() {
        val daysOfWeek = arrayOf("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")

        val t0 = 0L.secondsToSimulationDateTime()

        for (i in 0..6) {
            val t = t0.plusDays(i)
            testLogic(daysOfWeek, t, daysOfWeek[i])
        }
    }

    @Test
    fun invokeReturnsTrueOnMondayAtMidnight() {
        val t0 = 0L.secondsToSimulationDateTime()
        val t = t0.plusDays(2)
        val out = OncePerWeek("Monday")
        t.toDayOfWeekName().shouldBe("Monday")

        out.invoke(t).shouldBeTrue()
        out.invoke(t.minusSeconds(1)).shouldBeFalse()
        out.invoke(t.plusSeconds(1)).shouldBeFalse()
    }

    private fun testLogic(daysOfWeek: Array<String>, time: DateTime, dayOfWeek: String) {
        time.toDayOfWeekName().shouldBe(dayOfWeek)

        OncePerWeek(dayOfWeek).invoke(time).shouldBeTrue()
        daysOfWeek.filter { !it.equals(dayOfWeek) }.forEach {
            OncePerWeek(it).invoke(time).shouldBeFalse()
        }
    }
}
