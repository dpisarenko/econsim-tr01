package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.SolveInfo
import alice.tuprolog.Struct
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
        Assertions.assertThat(t1.timeTriggerFunction is OncePerWeek).isTrue()
        (t1.timeTriggerFunction as OncePerWeek).dayOfWeek.shouldBe("Monday")

        val t2 = findTransformation(out, "t2")
        if (t2 == null) {
            return
        }
        t2.agentId.shouldBe("stacy")
        t2.inputAmount.shouldBe(20.0)
        t2.inputResourceId.shouldBe("r13")
        t2.outputAmount.shouldBe(0.5)
        t2.outputResourceId.shouldBe("r15")
        (t2.timeTriggerFunction as OncePerWeek).dayOfWeek.shouldBe("Monday")
    }

    private fun findTransformation(out: Sim1ParametersProvider, id: String): PlTransformation? {
        val tr = out.transformations.filter { it.id == id }.firstOrNull()
        Assertions.assertThat(tr).isNotNull
        return tr
    }

    @Test
    fun extractFiringFunctionCreatesWhenResourceReachesLevel() {
        val out = Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        val res = mock<SolveInfo>()
        val timeFunctionPl = mock<Struct>()
        Mockito.`when`(res.getTerm("Time")).thenReturn(timeFunctionPl)
        Mockito.`when`(timeFunctionPl.name).thenReturn("whenResourceReachesLevel")
        // Run method under test
        out.extractFiringFunction(res)
        // Verify
        Mockito.verify(out).createWhenResourceReachesLevel(timeFunctionPl)
    }
}