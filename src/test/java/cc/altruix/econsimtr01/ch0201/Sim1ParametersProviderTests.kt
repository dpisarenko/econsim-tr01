package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.PlResource
import org.fest.assertions.Assertions
import org.junit.Test
import java.io.File

/**
 * Created by pisarenko on 08.04.2016.
 */
class Sim1ParametersProviderTests {
    @Test
    fun initReadsFlows() {
        val out = Sim1ParametersProvider(
                File("src/test/resources/ch0201Sim1Tests.params.pl").readText()
        )
        Assertions.assertThat(out.resources).isNotNull
        Assertions.assertThat(out.resources.size).isEqualTo(1)
        Assertions.assertThat(out.resources.get(0)).isEqualTo(
                PlResource(
                        "r1",
                        "Source code modification",
                        "Hits of code")
        )

    }
}