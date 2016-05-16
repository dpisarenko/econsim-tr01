/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
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
