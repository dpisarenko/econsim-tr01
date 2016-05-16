/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
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