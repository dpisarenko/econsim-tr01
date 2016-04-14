package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.SolveInfo
import alice.tuprolog.Struct
import cc.altruix.econsimtr01.*
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * Created by pisarenko on 14.04.2016.
 */
class Sim2ParametersProviderTests {
    @Test
    fun extractFiringFunctionCreatesOncePerWeekFunction() {
        val monday = Struct("Monday")
        val timeFunctionPl = mock<Struct>()
        Mockito.`when`(timeFunctionPl.name).thenReturn("oncePerWeek")
        Mockito.`when`(timeFunctionPl.getArg(0)).thenReturn(monday)
        val res = mock<SolveInfo>()
        Mockito.`when`(res.getTerm("Time")).thenReturn(timeFunctionPl)
        // Run method under test
        val actualResult = Sim2ParametersProvider("").extractFiringFunction(res)
        // Verify
        Assertions.assertThat(actualResult).isNotNull
        Assertions.assertThat(actualResult is OncePerWeek).isTrue()
        Assertions.assertThat((actualResult as OncePerWeek).dayOfWeek).isEqualTo("Monday")
    }

    @Test
    fun extractFiringFunctionCreatesAfterFunction() {
        val flowId = Struct("f1")

        val timeFunctionPl = mock<Struct>()
        Mockito.`when`(timeFunctionPl.name).thenReturn("after")
        Mockito.`when`(timeFunctionPl.getArg(0)).thenReturn(flowId)
        val res = mock<SolveInfo>()
        Mockito.`when`(res.getTerm("Time")).thenReturn(timeFunctionPl)
        // Run method under test
        val actualResult = Sim2ParametersProvider("").extractFiringFunction(res)
        // Verify
        Assertions.assertThat(actualResult).isNotNull
        Assertions.assertThat(actualResult is After).isTrue()
        Assertions.assertThat((actualResult as After).flowId).isEqualTo("f1")
    }

    @Test
    fun initInitsAfterTriggers() {
        // Run method under test
        val out = Sim2ParametersProvider("""
        isAgent(stacy).
        isAgent(list).
        isAgent(internets).

        resource(r1, "Message to the list", "Pieces").
        resource(r2, "Money", "2016 US dollars").
        resource(r3, "Accomodation", "Days the person is allowed to live in the flat").
        resource(r4, "Food", "Calories").
        resource(r5, "Copy of WordPress plugin X", "Pieces").

        hasFlow(f1,
            stacy,
            list,
            r1,
            1,
            oncePerWeek("Monday")).

        hasFlow(f2,
            list,
            stacy,
            r2,
            _,
            after(f1)).
        hasFlow(f3,
            stacy,
            list,
            r5,
            _,
            after(f1)).

        """)
        // Verify
        val f2 = findFlow(out, "f2")
        doAfterTriggerChecks(f2)

        val f3 = findFlow(out, "f3")
        doAfterTriggerChecks(f3)

        val f1 = findFlow(out, "f1")

        val after1 = f2.timeTriggerFunction
        val after2 = f3.timeTriggerFunction

        val monday = 0L.millisToSimulationDateTime().plusDays(2)

        val list = DefaultAgent("list")
        val stacy = DefaultAgent("stacy")


        f1.timeTriggerFunction.invoke(monday).shouldBeTrue()
        f1.agents = listOf(list, stacy)
        f1.flows = LinkedList<ResourceFlow>()

        (after1 as After).nextFireTime.shouldBe(-1)
        (after2 as After).nextFireTime.shouldBe(-1)

        f1.followingTriggers.contains(after1).shouldBeTrue()
        f1.followingTriggers.contains(after2).shouldBeTrue()

        f1.run(monday)

        after1.nextFireTime.shouldBe(monday.millis+1)
        after2.nextFireTime.shouldBe(monday.millis+1)
    }

    @Test
    fun readAgentsCreatesRightObjectForList() {
        val out = Sim2ParametersProvider("""
        isAgent(stacy).
        isAgent(list).
        isAgent(internets).

        resource(r1, "Message to the list", "Pieces").
        resource(r2, "Money", "2016 US dollars").
        resource(r3, "Accomodation", "Days the person is allowed to live in the flat").
        resource(r4, "Food", "Calories").
        resource(r5, "Copy of WordPress plugin X", "Pieces").

        hasFlow(f1,
            stacy,
            list,
            r1,
            1,
            oncePerWeek("Monday")).

        hasFlow(f2,
            list,
            stacy,
            r2,
            _,
            after(f1)).
        hasFlow(f3,
            stacy,
            list,
            r5,
            _,
            after(f1)).

        """)
        val list = out.agents.filter { it.id() == "list" }.first()
        Assertions.assertThat(list).isNotNull
        Assertions.assertThat(list is List).isTrue()
    }

    private fun doAfterTriggerChecks(f2: PlFlow) {
        Assertions.assertThat(f2).isNotNull
        Assertions.assertThat(f2.timeTriggerFunction is After).isTrue()
        Assertions.assertThat((f2.timeTriggerFunction as After).flowId).isEqualTo("f1")
    }

    private fun findFlow(out: Sim2ParametersProvider, id: String) = out.flows.filter { it.id == id }.first()
}