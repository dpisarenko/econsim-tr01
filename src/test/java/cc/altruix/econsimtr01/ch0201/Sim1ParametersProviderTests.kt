package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.PlResource
import cc.altruix.econsimtr01.toSimulationDateTime
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Assert
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

    @Test
    fun businessDaysTriggerFunctionReturnsTrueOnBusinessDaysAt1800() {
        val out = Sim1ParametersProvider(
                File("src/test/resources/ch0201Sim1Tests.params.pl").readText()
        )
        val t0 = 0L.toSimulationDateTime()
        Assertions.assertThat(t0.hourOfDay).isEqualTo(0)
        Assertions.assertThat(t0.minuteOfHour).isEqualTo(0)

        val day01800 = t0.plusHours(18)
        Assertions.assertThat(day01800.hourOfDay).isEqualTo(18)
        Assertions.assertThat(day01800.minuteOfHour).isEqualTo(0)

        businessDaysTestLogic(
                out,
                createDate(
                        t0,
                        0,
                        18,
                        0
                ),
                false
        )

/*
        businessDaysTestLogic(out, DateTime(2016, 4, 9, 17, 20), false)
        businessDaysTestLogic(out, DateTime(2016, 4, 9, 18, 0), false)
        businessDaysTestLogic(out, DateTime(2016, 4, 10, 17, 59), false)
        businessDaysTestLogic(out, DateTime(2016, 4, 10, 18, 0), false)

        businessDaysTestLogic(out, DateTime(2016, 4, 11, 18, 0), true)
        businessDaysTestLogic(out, DateTime(2016, 4, 12, 18, 0), true)
        businessDaysTestLogic(out, DateTime(2016, 4, 13, 18, 0), true)
        businessDaysTestLogic(out, DateTime(2016, 4, 14, 18, 0), true)
        businessDaysTestLogic(out, DateTime(2016, 4, 15, 18, 0), true)
        */
    }
    private fun createDate(t0:DateTime,
                           days:Int,
                           hours:Int,
                           minutes:Int):DateTime =
            t0.plusDays(days).plusHours(hours).plusMinutes(minutes)

    private fun businessDaysTestLogic(
            out: Sim1ParametersProvider,
            time: DateTime, expectedResult: Boolean) {
        Assert.assertEquals(expectedResult, out.businessDaysTriggerFunction()
                .invoke(time.millis))

    }
}