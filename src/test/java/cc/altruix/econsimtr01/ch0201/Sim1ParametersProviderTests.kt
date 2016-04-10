package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.PlFlow
import cc.altruix.econsimtr01.PlResource
import cc.altruix.econsimtr01.createDate
import cc.altruix.econsimtr01.millisToSimulationDateTime
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
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
        Assertions.assertThat(out.flows).isNotNull
        Assertions.assertThat(out.flows.size).isEqualTo(1)
        Assertions.assertThat(out.flows.get(0)).isEqualTo(
                PlFlow(
                        "f1",
                        "stacy",
                        "employer",
                        "r1",
                        null,
                        out.businessDaysTriggerFunction()
                )
        )
    }

    @Test
    fun initReadsFlowsCanReadMonetaryFlow() {
        val out = Sim1ParametersProvider(
                File("src/test/resources/ch0201Sim1Tests.params2.pl").readText()
        )
        Assertions.assertThat(out.flows).isNotNull
        Assertions.assertThat(out.flows.size).isEqualTo(1)
        Assertions.assertThat(out.flows.get(0)).isEqualTo(
                PlFlow(
                        "f2",
                        "employer",
                        "stacy",
                        "r2",
                        3000.0,
                        out.businessDaysTriggerFunction()
                )
        )
    }

    @Test
    fun businessDaysTriggerFunctionReturnsTrueOnBusinessDaysAt1800() {
        val out = Sim1ParametersProvider(
                File("src/test/resources/ch0201Sim1Tests.params.pl").readText()
        )
        val t0 = 0L.millisToSimulationDateTime()
        Assertions.assertThat(t0.hourOfDay).isEqualTo(0)
        Assertions.assertThat(t0.minuteOfHour).isEqualTo(0)

        val day01800 = t0.plusHours(18)
        Assertions.assertThat(day01800.hourOfDay).isEqualTo(18)
        Assertions.assertThat(day01800.minuteOfHour).isEqualTo(0)

        Assertions.assertThat(day01800.dayOfWeek).isEqualTo(DateTimeConstants.SATURDAY)

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
        businessDaysTestLogic(
                out,
                day01800,
                false
        )

        val day11800 = day01800.plusHours(24)
        Assertions.assertThat(day11800.hourOfDay).isEqualTo(18)
        Assertions.assertThat(day11800.minuteOfHour).isEqualTo(0)
        Assertions.assertThat(day11800.dayOfWeek).isEqualTo(DateTimeConstants.SUNDAY)

        businessDaysTestLogic(
                out,
                day11800,
                false
        )

        val day21800 = day11800.plusHours(24)
        Assertions.assertThat(day21800.hourOfDay).isEqualTo(18)
        Assertions.assertThat(day21800.minuteOfHour).isEqualTo(0)
        Assertions.assertThat(day21800.dayOfWeek).isEqualTo(DateTimeConstants.MONDAY)

        validateBusinessDayFunction(day21800, out)

        val day31800 = day21800.plusHours(24)
        Assertions.assertThat(day31800.hourOfDay).isEqualTo(18)
        Assertions.assertThat(day31800.minuteOfHour).isEqualTo(0)
        Assertions.assertThat(day31800.dayOfWeek).isEqualTo(DateTimeConstants.TUESDAY)

        validateBusinessDayFunction(day31800, out)

        val day41800 = day31800.plusHours(24)
        Assertions.assertThat(day41800.hourOfDay).isEqualTo(18)
        Assertions.assertThat(day41800.minuteOfHour).isEqualTo(0)
        Assertions.assertThat(day41800.dayOfWeek).isEqualTo(DateTimeConstants.WEDNESDAY)

        validateBusinessDayFunction(day41800, out)

        val day51800 = day41800.plusHours(24)
        Assertions.assertThat(day51800.hourOfDay).isEqualTo(18)
        Assertions.assertThat(day51800.minuteOfHour).isEqualTo(0)
        Assertions.assertThat(day51800.dayOfWeek).isEqualTo(DateTimeConstants.THURSDAY)
        validateBusinessDayFunction(day51800, out)

        val day61800 = day51800.plusHours(24)
        Assertions.assertThat(day61800.hourOfDay).isEqualTo(18)
        Assertions.assertThat(day61800.minuteOfHour).isEqualTo(0)
        Assertions.assertThat(day61800.dayOfWeek).isEqualTo(DateTimeConstants.FRIDAY)
        validateBusinessDayFunction(day61800, out)
    }

    private fun validateBusinessDayFunction(day: DateTime, out: Sim1ParametersProvider) {
        businessDaysTestLogic(
                out,
                day,
                true
        )
        businessDaysTestLogic(
                out,
                day.plusSeconds(1),
                false
        )
        businessDaysTestLogic(
                out,
                day.minusSeconds(1),
                false
        )
    }

    @Test
    fun oncePerMonthTriggerFunctionSunnyDay() {
        val out = Sim1ParametersProvider(
                File("src/test/resources/ch0201Sim1Tests.params.pl").readText()
        )
        // TODO: Finish this test
    }

    private fun businessDaysTestLogic(
            out: Sim1ParametersProvider,
            time: DateTime, expectedResult: Boolean) {
        Assert.assertEquals(expectedResult,
                out.businessDaysTriggerFunction()
                .invoke(time))
    }
}
