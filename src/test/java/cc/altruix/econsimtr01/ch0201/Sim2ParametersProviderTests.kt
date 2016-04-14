package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.SolveInfo
import alice.tuprolog.Struct
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
        val timeFunctionPl = Mockito.mock(Struct::class.java)
        Mockito.`when`(timeFunctionPl.name).thenReturn("oncePerWeek")
        Mockito.`when`(timeFunctionPl.getArg(0)).thenReturn(monday)
        val res = Mockito.mock(SolveInfo::class.java)
        Mockito.`when`(res.getTerm("Time")).thenReturn(timeFunctionPl)
        // Run method under test
        val actualResult = Sim2ParametersProvider("").extractFiringFunction(res)
        // Verify
        Assertions.assertThat(actualResult).isNotNull
        Assertions.assertThat(actualResult is OncePerWeek).isTrue()
        Assertions.assertThat((actualResult as OncePerWeek).dayOfWeek).isEqualTo("Monday")
    }
}