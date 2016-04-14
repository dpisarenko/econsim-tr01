package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.SolveInfo
import alice.tuprolog.Struct
import cc.altruix.econsimtr01.mock
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by pisarenko on 14.04.2016.
 */
class Sim2ParametersProviderTests {
    @Test
    fun extractFiringFunctionCreatesOncePerWeekFunction() {
        val monday = Struct("Monday")
        val timeFunctionPl = Struct::class.mock()
        Mockito.`when`(timeFunctionPl.name).thenReturn("oncePerWeek")
        Mockito.`when`(timeFunctionPl.getArg(0)).thenReturn(monday)
        val res = SolveInfo::class.mock()
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
        val timeFunctionPl = Struct::class.mock()
        Mockito.`when`(timeFunctionPl.name).thenReturn("after")
        Mockito.`when`(timeFunctionPl.getArg(0)).thenReturn(flowId)
        val res = SolveInfo::class.mock()
        Mockito.`when`(res.getTerm("Time")).thenReturn(timeFunctionPl)
        // Run method under test
        val actualResult = Sim2ParametersProvider("").extractFiringFunction(res)
        // Verify
        Assertions.assertThat(actualResult).isNotNull
        Assertions.assertThat(actualResult is After).isTrue()
        Assertions.assertThat((actualResult as After).flowId).isEqualTo("f1")

    }
}