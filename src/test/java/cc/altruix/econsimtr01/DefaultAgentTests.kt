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
        val out = ListAgent("list")
        val s1 = Subscriber("0", 0)
        val s2 = Subscriber("1", 1)
        val s3 = Subscriber("2", 2)
        out.subscribers.add(s1)
        out.subscribers.add(s2)
        out.subscribers.add(s3)
        // Run method under test
        out.updateInteractionsCount()
        // Verify
        s1.interactionsWithStacy.shouldBe(1)
        s2.interactionsWithStacy.shouldBe(2)
        s3.interactionsWithStacy.shouldBe(3)
    }
}