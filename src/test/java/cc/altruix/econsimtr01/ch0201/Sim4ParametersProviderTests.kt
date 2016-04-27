package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.SolveInfo
import alice.tuprolog.Struct
import cc.altruix.econsimtr01.PlFlow
import cc.altruix.econsimtr01.PlTransformation
import cc.altruix.econsimtr01.mock
import cc.altruix.econsimtr01.shouldBe
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.io.File

/**
 * Created by pisarenko on 19.04.2016.
 */
class Sim4ParametersProviderTests {
    @Test
    fun initReadsTransformations() {
        val out = Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        out.transformations.size.shouldBe(2)

        val t1 = findTransformation(out, "t1")
        if (t1 == null) {
            return
        }
        t1.agentId.shouldBe("stacy")
        t1.inputAmount.shouldBe(20.0)
        t1.inputResourceId.shouldBe("r13")
        t1.outputAmount.shouldBe(20.0)
        t1.outputResourceId.shouldBe("r14")
        Assertions.assertThat(t1.timeTriggerFunction is After).isTrue()
        (t1.timeTriggerFunction as After).flowId.shouldBe("f7")

        val t2 = findTransformation(out, "t2")
        if (t2 == null) {
            return
        }
        t2.agentId.shouldBe("stacy")
        t2.inputAmount.shouldBe(20.0)
        t2.inputResourceId.shouldBe("r13")
        t2.outputAmount.shouldBe(0.5)
        t2.outputResourceId.shouldBe("r15")
        Assertions.assertThat(t2.timeTriggerFunction is After).isTrue()
        (t2.timeTriggerFunction as After).flowId.shouldBe("f7")
    }

    private fun findTransformation(out: Sim1ParametersProvider, id: String): PlTransformation? {
        val tr = out.transformations.filter { it.id == id }.firstOrNull()
        Assertions.assertThat(tr).isNotNull
        return tr
    }

    @Test
    fun extractFiringFunctionCreatesWhenResourceReachesLevel() {
        val out = Mockito.spy(Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        ))
        val res = mock<SolveInfo>()
        val timeFunctionPl = mock<Struct>()
        Mockito.`when`(res.getTerm("Time")).thenReturn(timeFunctionPl)
        Mockito.`when`(timeFunctionPl.name).thenReturn("whenResourceReachesLevel")
        Mockito.`when`(timeFunctionPl.getArg(0)).thenReturn(Struct("stacy"))
        Mockito.`when`(timeFunctionPl.getArg(1)).thenReturn(Struct("r15"))
        Mockito.`when`(timeFunctionPl.getArg(2)).thenReturn(alice.tuprolog.Double(1.0))
        // Run method under test
        val act = out.extractFiringFunction(res)
        // Verify
        Assertions.assertThat(act).isNotNull
        Assertions.assertThat(act is WhenResourceReachesLevel).isTrue()
        val trigger = act as WhenResourceReachesLevel
        trigger.agent.shouldBe("stacy")
        trigger.resource.shouldBe("r15")
        trigger.amount.shouldBe(1.0)
        Mockito.verify(out).createWhenResourceReachesLevel(timeFunctionPl)
    }
    @Test
    fun createWhenResourceReachesLevel() {
        val out = Mockito.spy(Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        ))
        val timeFunctionPl = mock<Struct>()
        Mockito.`when`(timeFunctionPl.getArg(0)).thenReturn(Struct("stacy"))
        Mockito.`when`(timeFunctionPl.getArg(1)).thenReturn(Struct("r15"))
        Mockito.`when`(timeFunctionPl.getArg(2)).thenReturn(alice.tuprolog.Double(1.0))
        // Run method under test
        val act = out.createWhenResourceReachesLevel(timeFunctionPl)
        // Verify
        Assertions.assertThat(act).isNotNull
        Assertions.assertThat(act is WhenResourceReachesLevel).isTrue()
        val trigger = act as WhenResourceReachesLevel
        trigger.agent.shouldBe("stacy")
        trigger.resource.shouldBe("r15")
        trigger.amount.shouldBe(1.0)
    }

    @Test
    fun initWhenResourceReachesLevel() {
        val out = Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        out.flows.clear()
        val trigger = Mockito.spy(WhenResourceReachesLevel("stacy", "r15", 1.0))
        Mockito.doNothing().`when`(trigger).connectToInitiatingAgent(out.agents)
        val flow = PlFlow("f8", "stacy", "list", "r15", 1.0, trigger)
        out.flows.add(flow)
        // Run method under test
        out.initWhenResourceReachesLevel()
        // Verify
        Mockito.verify(trigger).connectToInitiatingAgent(out.agents)
    }
}