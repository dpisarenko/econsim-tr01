/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01

import cc.altruix.econsimtr01.ch0201.ListAgent
import cc.altruix.econsimtr01.ch0201.Subscriber
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by pisarenko on 15.04.2016.
 */
class DefaultAgentTests {
    @Test
    fun actNotifiesSubscribers() {
        val t = 0L.millisToSimulationDateTime()
        val action1 = mock<IAction>()
        Mockito.`when`(action1.timeToRun(t)).thenReturn(true)
        val action2 = mock<IAction>()
        Mockito.`when`(action2.timeToRun(t)).thenReturn(false)
        val out = DefaultAgent("agent1")
        out.actions.add(action1)
        out.actions.add(action2)
        // Run method under test
        out.act(t)
        // Verify
        Mockito.verify(action1).notifySubscribers(t)
        Mockito.verify(action2, Mockito.never()).notifySubscribers(t)
    }
    @Test
    fun subscriptionWorks() {
        val subscriber = mock<IActionSubscriber>()
        val out = PlFlow("f1", "src", "target", "res", null, { true })
        val t = 0L.millisToSimulationDateTime()
        // Run method under test
        out.subscribe(subscriber)
        out.notifySubscribers(t)
        // Verify
        Mockito.verify(subscriber).actionOccurred(out, t)
    }
    @Test
    fun actionOccurredCallsUpdateInteractionsCount() {
        val out = Mockito.spy(ListAgent("list"))
        // Run method under test
        out.actionOccurred(mock<IAction>(), 0L.millisToSimulationDateTime())
        // Verify
        Mockito.verify(out).updateInteractionsCount()
    }

    @Test
    fun updateInteractionsCountSunnyDay() {
        updateInteractionsCountTestLogic(3, 1)
        updateInteractionsCountTestLogic(4, 2)
    }

    private fun updateInteractionsCountTestLogic(subscriberCount: Int, expectedNumberOfUpdatedSubscribers: Int) {
        val out = ListAgent("list")
        for (i in 1..subscriberCount) {
            out.subscribers.add(Subscriber("$i", 0))
        }
        // Run method under test
        out.updateInteractionsCount()
        // Verify
        var numberOfUpdatedSubscribers = out.subscribers
                .filter { it.interactionsWithStacy > 0 }
                .count()
        numberOfUpdatedSubscribers.shouldBe(expectedNumberOfUpdatedSubscribers)
    }

    @Test
    fun subscribersBuySunnyDay() {
        val out = ListAgent("list")
        for (i in 1..90) {
            out.subscribers.add(Subscriber(i.toString(), 7))
        }
        for (i in 1..10) {
            out.subscribers.add(Subscriber(i.toString(), 8))
        }
        for (i in 1..10) {
            out.subscribers.add(Subscriber(i.toString(), 6))
        }
        // Run method under test
        val result = out.subscribersBuy()
        // Verify
        result.shouldBe(10)
        out.subscribers.filter { it.boughtSomething }.count().shouldBe(10)
    }

    @Test
    fun actionOccurredSunnyDay() {
        val out = Mockito.spy(ListAgent("list"))
        Mockito.doNothing().`when`(out).updateInteractionsCount()
        Mockito.doReturn(123).`when`(out).subscribersBuy()
        out.buyersCount.shouldBe(0)
        // Run method under test
        out.actionOccurred(
                mock<IAction>(),
                0L.millisToSimulationDateTime()
        )
        // Verify
        Mockito.verify(out).subscribersBuy()
        out.buyersCount.shouldBe(123)
    }
    @Test
    fun transformationsAreExecuted() {
        val out = Mockito.spy(ListAgent("list"))
        val t = 0L.millisToSimulationDateTime()
        val tr = mock<PlTransformation>()
        Mockito.`when`(tr.timeToRun(t)).thenReturn(true)
        // Run method under test
        out.addTransformation(tr)
        // Verify
        out.actions.contains(tr).shouldBeTrue()
        // Run method under test
        out.act(t)
        // Verify
        Mockito.verify(tr).run(t)
        Mockito.verify(tr).notifySubscribers(t)
    }
    @Test
    fun addResourceLevelObserver() {
        val out = DefaultAgent("agent")
        val rlo = mock<IResourceLevelObserver>()
        out.addResourceLevelObserver(rlo)
        val t = 0L.millisToSimulationDateTime()
        // Run method under test
        out.act(t)
        // Verify
        Mockito.verify(rlo).possibleResourceLevelChange(out, t)
    }
}