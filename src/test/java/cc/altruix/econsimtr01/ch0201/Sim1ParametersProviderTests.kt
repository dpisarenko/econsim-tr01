package cc.altruix.econsimtr01.ch0201

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

    }
}