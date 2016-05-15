package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 14.05.2016.
 */
class AgriculturalSimParametersProviderTests {
    @Test
    fun ryeSimulationParametersCorrect() {
        simulationParametersCorrectnessTestLogic("BasicAgriculturalSimulationRye.properties")
    }
    @Test
    fun wheatSimulationParametersCorrect() {
        simulationParametersCorrectnessTestLogic("BasicAgriculturalSimulationWheat.properties")
    }

    private fun simulationParametersCorrectnessTestLogic(fileName: String) {
        val out = AgriculturalSimParametersProvider(File("src/test/resources/ch03/$fileName"))
        out.initAndValidate()
        Assertions.assertThat(out.validity.valid).isTrue()
        Assertions.assertThat(out.validity.message).isEmpty()
    }
}