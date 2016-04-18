package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import alice.tuprolog.SolveInfo
import alice.tuprolog.Struct
import cc.altruix.econsimtr01.*
import cc.altruix.javaprologinterop.PlUtils
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.io.File
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

        if ((f1 == null) || (f2 == null) || (f3 == null)) {
            Assertions.assertThat(f1).isNotNull
            Assertions.assertThat(f2).isNotNull
            Assertions.assertThat(f3).isNotNull
            return
        }

        val after1 = f2.timeTriggerFunction
        val after2 = f3.timeTriggerFunction

        val monday = 0L.millisToSimulationDateTime().plusDays(2)

        val list = DefaultAgent("list")
        val stacy = DefaultAgent("stacy")


        f1.timeTriggerFunction.invoke(monday).shouldBeTrue()
        f1.agents = listOf(list, stacy)
        f1.flows = LinkedList<ResourceFlow>()

        Assertions.assertThat((after1 as After).nextFireTime).isNull()
        Assertions.assertThat((after2 as After).nextFireTime).isNull()

        f1.followingTriggers.contains(after1).shouldBeTrue()
        f1.followingTriggers.contains(after2).shouldBeTrue()

        f1.run(monday)

        Assertions.assertThat(after1.nextFireTime).isEqualTo(monday.plusMinutes(1))
        Assertions.assertThat(after2.nextFireTime).isEqualTo(monday.plusMinutes(1))
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
        Assertions.assertThat(list is ListAgent).isTrue()

        out.agents.size.shouldBe(3)
        out.agents.filter { it.id() != "list" }.forEach { (it is DefaultAgent).shouldBeTrue() }
    }

    @Test
    fun readAgentsReadsListAgentParameters() {
        // Run method under test
        val out = Sim2ParametersProvider("""
        isAgent(list).
        percentageOfReaders(0.6).
        interactionsBeforePurchase(8).
        percentageOfBuyers(0.2).
        """)
        // Verify
        val listAgents = out.agents.filter { it.id() == "list" }
        listAgents.size.shouldBe(1)
        val list = listAgents.first() as ListAgent
        list.percentageOfReaders.shouldBe(0.6)
        list.interactionsBeforePurchase.shouldBe(8)
        list.percentageOfBuyers.shouldBe(0.2)
    }

    @Test
    fun createFlowCallsCreateF2() {
        val fdata = Sim1ParametersProvider.ExtractFlowDataResult("f2",
                "src",
                "target",
                "resource",
                null,
                {true})
        val prolog = mock<Prolog>()
        val out = Mockito.spy(Sim2ParametersProvider(""))
        val res = mock<SolveInfo>()
        Mockito.doReturn(fdata).`when`(out).extractFlowData(res)
        val flow = mock<PlFlow>()
        Mockito.doReturn(flow).`when`(out).createF2(fdata, prolog)
        // Run method under test
        val act = out.createFlow(res, prolog)
        // Verify
        Assertions.assertThat(act).isSameAs(flow)
        Mockito.verify(out).createF2(fdata, prolog)
        Mockito.verify(out, Mockito.never()).createF3(fdata, prolog)
    }
    @Test
    fun createFlowCallsCreateF3() {
        val fdata = Sim1ParametersProvider.ExtractFlowDataResult("f3",
                "src",
                "target",
                "resource",
                null,
                {true})
        val prolog = mock<Prolog>()
        val out = Mockito.spy(Sim2ParametersProvider(""))
        val res = mock<SolveInfo>()
        Mockito.doReturn(fdata).`when`(out).extractFlowData(res)
        val flow = mock<PlFlow>()
        Mockito.doReturn(flow).`when`(out).createF3(fdata, prolog)
        // Run method under test
        val act = out.createFlow(res, prolog)
        // Verify
        Assertions.assertThat(act).isSameAs(flow)
        Mockito.verify(out, Mockito.never()).createF2(fdata, prolog)
        Mockito.verify(out).createF3(fdata, prolog)
    }
    @Test
    fun createFlowCallsDoesntCallF2F3Methods() {
        val fdata = Sim1ParametersProvider.ExtractFlowDataResult("f4",
                "src",
                "target",
                "resource",
                null,
                {true})
        val prolog = mock<Prolog>()
        val out = Mockito.spy(Sim2ParametersProvider(""))
        val res = mock<SolveInfo>()
        Mockito.doReturn(fdata).`when`(out).extractFlowData(res)
        val flow = mock<PlFlow>()
        Mockito.doReturn(flow).`when`(out).createF2(fdata, prolog)
        // Run method under test
        val act = out.createFlow(res, prolog)
        // Verify
        Mockito.verify(out, Mockito.never()).createF2(fdata, prolog)
        Mockito.verify(out, Mockito.never()).createF3(fdata, prolog)
    }

    @Test
    fun createF2SunnyDay() {
        val out = Mockito.spy(Sim2ParametersProvider(""))
        val fdata = Sim1ParametersProvider.ExtractFlowDataResult(
                "id",
                "src",
                "target",
                "resource",
                123.45,
                {true}
        )
        val prolog = mock<Prolog>()
        Mockito.doReturn(40.0).`when`(out).readPriceOfOneCopyOfSoftware(prolog)
        // Run method under test
        val act = out.createF2(fdata, prolog)
        // Verify
        Assertions.assertThat(act is F2Flow).isTrue()
        val f2 = act as F2Flow
        f2.id.shouldBe("id")
        f2.src.shouldBe("src")
        f2.target.shouldBe("target")
        f2.resource.shouldBe("resource")
        Assertions.assertThat(f2.amount).isNotNull
        f2.amount?.shouldBe(123.45)
        Assertions.assertThat(f2.timeTriggerFunction).isSameAs(fdata.timeFunction)
    }

    @Test
    fun createF3SunnyDay() {
        val out = Sim2ParametersProvider("")
        val fdata = Sim1ParametersProvider.ExtractFlowDataResult(
                "id",
                "src",
                "target",
                "resource",
                123.45,
                {true}
        )
        val prolog = mock<Prolog>()
        // Run method under test
        val act = out.createF3(fdata, prolog)
        // Verify
        Assertions.assertThat(act is F3Flow).isTrue()
        val f3 = act as F3Flow
        f3.id.shouldBe("id")
        f3.src.shouldBe("src")
        f3.target.shouldBe("target")
        f3.resource.shouldBe("resource")
        Assertions.assertThat(f3.amount).isNotNull
        f3.amount?.shouldBe(123.45)
        Assertions.assertThat(f3.timeTriggerFunction).isSameAs(fdata.timeFunction)
    }

    @Test
    fun initListRelatedFlowsSunnyDay() {
        val out = Mockito.spy(Sim2ParametersProvider(""))
        val listAgent = ListAgent("list")
        val agents = emptyList<IAgent>()
        val prolog = mock<Prolog>()
        val f2 = F2Flow("f2", "src", "target", "resource", 123.45, {true}, 122.3)
        val f3 = F2Flow("f3", "src", "target", "resource", 123.45, {true}, 123.3)
        val otherFlow = PlFlow("f1", "src", "target", "resource", 123.45, {true})
        Mockito.doReturn(listAgent).`when`(out).findListAgent(agents)
        val flows = LinkedList<PlFlow>()
        // Run method under test
        out.initListRelatedFlows(agents, listOf(f2, f3, otherFlow))
        // Verify
        Assertions.assertThat(f2.list).isSameAs(listAgent)
        Assertions.assertThat(f3.list).isSameAs(listAgent)
    }

    @Test
    fun initCallsInitListRelatedFlows() {
        // Run method under test
        val out = Sim2ParametersProvider("""
            isAgent(stacy).
            isAgent(list).
            hasFlow(f2,
                list,
                stacy,
                r2,
                _, % priceOfSoftwareSoldToNewlyActivatedAudience()
                after(f1)).
            hasFlow(f3,
                stacy,
                list,
                r5,
                _, % numberOfCopiesOfSoftwareSoldToNewlyActivatedAudience()
                after(f1)).
        """)
        // Verify
        val f2 = findFlow(out, "f2")
        val f3 = findFlow(out, "f3")

        Assertions.assertThat(f2).isNotNull
        Assertions.assertThat(f2 is ListRelatedFlow).isTrue()
        Assertions.assertThat(f3).isNotNull
        Assertions.assertThat(f3 is ListRelatedFlow).isTrue()

        val list = out.agents.filter { it is ListAgent }.firstOrNull()
        Assertions.assertThat(list).isNotNull

        Assertions.assertThat((f2 as ListRelatedFlow).list).isSameAs(list)
        Assertions.assertThat((f3 as ListRelatedFlow).list).isSameAs(list)
    }

    @Test
    fun findListAgentSunnyDay() {
        val out = Sim2ParametersProvider("")
        val agent = DefaultAgent("a1")
        val list = ListAgent("list")
        // Run method under test
        val act = out.findListAgent(listOf(agent, list))
        // Verify
        Assertions.assertThat(act).isNotNull
        Assertions.assertThat(act).isSameAs(list)
    }

    @Test
    fun readPriceOfOneCopyOfSoftwareSunnyDay() {
        val prolog = "priceOfOneCopyOfSoftware(123.45).".toPrologTheory()
        val out = Sim2ParametersProvider("")
        // Run method under test
        val act = out.readPriceOfOneCopyOfSoftware(prolog)
        // Verify
        act.shouldBe(123.45)
    }

    @Test
    fun createF2CallsReadPriceOfOneCopyOfSoftware() {
        val priceOfOneCopyOfSoftware = 123.45
        val out = Mockito.spy(Sim2ParametersProvider(""))
        val fdata = Sim1ParametersProvider.ExtractFlowDataResult(
            "id",
            "src",
            "target",
            "resource",
            null,
            {false}
        )
        val prolog = PlUtils.createEngine()
        Mockito.doReturn(priceOfOneCopyOfSoftware).`when`(out).
                readPriceOfOneCopyOfSoftware(prolog)
        // Run method under test
        val act = out.createF2(fdata, prolog)
        // Verify
        Mockito.verify(out).readPriceOfOneCopyOfSoftware(prolog)
    }

    @Test
    fun initSubscribesListAgentToF1() {
        val out = Sim2ParametersProvider(
                File("src/test/resources/ch0201Sim2Tests.params.pl").readText()
        )
        val f1 = out.flows.filter { it.id == "f1" }.firstOrNull()
        Assertions.assertThat(f1).isNotNull
        if (f1 == null) {
            return
        }
        f1.subscribers.size.shouldBe(1)
        Assertions.assertThat(f1.subscribers.get(0) is ListAgent).isTrue()
    }

    private fun doAfterTriggerChecks(f2: PlFlow?) {
        Assertions.assertThat(f2).isNotNull
        if (f2 == null) {
            return
        }
        Assertions.assertThat(f2.timeTriggerFunction is After).isTrue()
        Assertions.assertThat((f2.timeTriggerFunction as After).flowId).isEqualTo("f1")
    }

    private fun findFlow(out: Sim2ParametersProvider, id: String) =
            out.flows.filter { it.id == id }.firstOrNull()
}
