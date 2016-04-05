package cc.altruix.econsimtr01

import org.apache.commons.io.IOUtils
import org.fest.assertions.Assertions
import org.junit.Test
import java.io.File
import java.io.FileInputStream

/**
 * Created by pisarenko on 05.04.2016.
 */
class SimParametersProviderTests {
    @Test
    fun sunnyDay() {
        val fileInputStream =
                FileInputStream(
                        File("src/test/resources/Simulation1.params.pl")
                )
        val out = SimParametersProvider(fileInputStream)
        Assertions.assertThat(out.maxDaysWithoutFood).isEqualTo(30)
        Assertions.assertThat(out.initialAmountOfPotatoes).isEqualTo(90.0)
        Assertions.assertThat(out.dailyPotatoConsumption).isEqualTo(1.0)
    }
}