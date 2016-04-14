package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.shouldBe
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by pisarenko on 14.04.2016.
 */
class ListAgentTests {
    @Test
    fun putCallsAddSubscribersIfItsASubscriberResource() {
        ListAgent.subscriberTypes.keys.forEach {
            val out = Mockito.spy(ListAgent("list"))
            out.put(it, 123.0)
            Mockito.verify(out).addSubscribers(it, 123.0)
        }
    }
    @Test
    fun putHandlesNonSubscriberResourcesCorrectly() {
        arrayOf("r1", "r2", "r3", "r4", "r5").forEach {
            val out = Mockito.spy(ListAgent("list"))
            Assertions.assertThat(out.amount(it)).isEqualTo(0.0)
            out.put(it, 123.0)
            Assertions.assertThat(out.amount(it)).isEqualTo(123.0)
            Mockito.verify(out, Mockito.never()).addSubscribers(
                    Mockito.anyString(),
                    Mockito.anyDouble()
            )
        }
    }
    @Test
    fun addSubscribersSunnyDay() {
        ListAgent.subscriberTypes.entries.forEach {
            val out = ListAgent("list")
            out.subscribers.size.shouldBe(0)
            out.addSubscribers(it.key, 3.5)

            out.subscribers.size.shouldBe(4)
            out.subscribers.forEach { x ->
                x.interactionsWithStacy.shouldBe(it.value)
            }
        }
    }
}