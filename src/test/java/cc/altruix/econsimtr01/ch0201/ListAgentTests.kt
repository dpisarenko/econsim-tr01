/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.PlFlow
import cc.altruix.econsimtr01.shouldBe
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.util.*

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
    @Test
    fun addActionSubscribesListToF1Flow() {
        val f1 = mockFlow("f1")
        val f2 = mockFlow("f2")
        val out = ListAgent("list")
        // Run method under test
        out.addAction(f1)
        out.addAction(f2)
        // Verify
        Mockito.verify(f1).subscribe(out)
        Mockito.verify(f2, Mockito.never()).subscribe(out)
    }

    @Test
    fun interactionsCountUpdateIsDeterministic() {
        for (j in 1..10) {
            interactionsCountUpdateIsDeterministicTestLogic()
        }
    }

    private fun interactionsCountUpdateIsDeterministicTestLogic() {
        val out = ListAgent("list")
        for (i in 1..10) {
            out.subscribers.add(
                    Subscriber(
                            UUID.randomUUID().toString(),
                            1)
            )
            out.subscribers.add(
                    Subscriber(
                            UUID.randomUUID().toString(),
                            2)
            )
        }

        countSubscribers(out, 1).shouldBe(10)
        countSubscribers(out, 2).shouldBe(10)
        countSubscribers(out, 3).shouldBe(0)

        out.updateInteractionsCount()

        countSubscribers(out, 1).shouldBe(6)
        countSubscribers(out, 2).shouldBe(8)
        countSubscribers(out, 3).shouldBe(6)

        out.updateInteractionsCount()

        countSubscribers(out, 1).shouldBe(3)
        countSubscribers(out, 2).shouldBe(7)
        countSubscribers(out, 3).shouldBe(7)
    }

    @Test
    fun getIndicesOfSubscribersToUpdateIsDeterministic() {
        for (j in 1..100) {
            getIndicesOfSubscribersToUpdateIsDeterministicTestLogic()
        }

    }

    private fun getIndicesOfSubscribersToUpdateIsDeterministicTestLogic() {
        val out = ListAgent("list")
        for (i in 1..10) {
            out.subscribers.add(
                    Subscriber(
                            UUID.randomUUID().toString(),
                            1)
            )
            out.subscribers.add(
                    Subscriber(
                            UUID.randomUUID().toString(),
                            2)
            )
        }
        out.getIndicesOfSubscribersToUpdate(out.subscribers, out.percentageOfReaders).size.shouldBe(10)
    }

    private fun countSubscribers(out: ListAgent, interactions: Int) =
            out.subscribers.filter { it.interactionsWithStacy == interactions }.count()

    private fun mockFlow(id: String): PlFlow =
            Mockito.spy(PlFlow(id, "src", "target", "resource", null, {true}))


    /**
     * We need the random number generator to always return the same random numbers in order
     * for the results to be repeatable.
     */
    @Test
    fun randomNumberGeneratorHasFixedSeed() {
        val out = ListAgent("list")
        val expected = arrayOf(1398255702, 1071063251, 850666878, -758044659, -6236124, 866330746, 776471069)
        val actual = expected.map { out.random.nextInt() }.toIntArray()
        for (i in 0..(expected.size-1)) {
            actual[i].shouldBe(expected[i])
        }
    }

    @Test
    fun subscribersBuyPassesTheRightListToUpdateBoughtSomethingProperty() {
        val out = Mockito.spy(ListAgent("list"))
        val potentialBuyers = emptyList<Subscriber>()
        Mockito.doReturn(potentialBuyers).`when`(out).calculatePotentialBuyers()
        val indices = emptyList<Int>()
        Mockito.doReturn(indices).`when`(out).getIndicesOfSubscribersToUpdate(potentialBuyers, out.percentageOfBuyers)
        Mockito.doNothing().`when`(out).updateBoughtSomethingProperty(indices, potentialBuyers)
        // Run method under test
        out.subscribersBuy()
        // Verify
        Mockito.verify(out).calculatePotentialBuyers()
        Mockito.verify(out).getIndicesOfSubscribersToUpdate(potentialBuyers, out.percentageOfBuyers)
        Mockito.verify(out).updateBoughtSomethingProperty(indices, potentialBuyers)
    }
}