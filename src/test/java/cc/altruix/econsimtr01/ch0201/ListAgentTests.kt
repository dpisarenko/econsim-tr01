package cc.altruix.econsimtr01.ch0201

import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by pisarenko on 14.04.2016.
 */
class ListAgentTests {
    @Test
    fun putCallsAddSubscribersIfItsASubscriberResource() {
        ListAgent.subscriberTypes.forEach {
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
}