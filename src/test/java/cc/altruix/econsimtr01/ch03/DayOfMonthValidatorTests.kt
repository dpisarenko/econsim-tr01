package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.junit.Test
import java.util.*

/**
 * Created by pisarenko on 15.05.2016.
 */
class DayOfMonthValidatorTests {
    @Test
    fun validateDetectsInvalidFormat() {
        val data = Properties()
        data["someParam"] = "3008"
        val res = DayOfMonthValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo("Value '3008' of 'someParam' has invalid format")
    }
    @Test
    fun validateDetectsWrongDay() {
        wrongDayTestLogic("30.02")
        wrongDayTestLogic("31.02")
        wrongDayTestLogic("31.04")
        wrongDayTestLogic("31.06")
        wrongDayTestLogic("31.09")
        wrongDayTestLogic("31.11")
        wrongDayTestLogic("32.01")
        wrongDayTestLogic("32.02")
        wrongDayTestLogic("32.03")
        wrongDayTestLogic("32.04")
        wrongDayTestLogic("32.05")
        wrongDayTestLogic("32.06")
        wrongDayTestLogic("32.07")
        wrongDayTestLogic("32.08")
        wrongDayTestLogic("32.09")
        wrongDayTestLogic("32.10")
        wrongDayTestLogic("32.11")
        wrongDayTestLogic("32.12")
        wrongDayTestLogic("0.01")
        wrongDayTestLogic("0.02")
        wrongDayTestLogic("0.03")
        wrongDayTestLogic("0.04")
        wrongDayTestLogic("0.05")
        wrongDayTestLogic("0.06")
        wrongDayTestLogic("0.07")
        wrongDayTestLogic("0.08")
        wrongDayTestLogic("0.09")
        wrongDayTestLogic("0.10")
        wrongDayTestLogic("0.11")
        wrongDayTestLogic("0.12")
        wrongDayTestLogic("00.01")
        wrongDayTestLogic("00.02")
        wrongDayTestLogic("00.03")
        wrongDayTestLogic("00.04")
        wrongDayTestLogic("00.05")
        wrongDayTestLogic("00.06")
        wrongDayTestLogic("00.07")
        wrongDayTestLogic("00.08")
        wrongDayTestLogic("00.09")
        wrongDayTestLogic("00.10")
        wrongDayTestLogic("00.11")
        wrongDayTestLogic("00.12")
        wrongDayTestLogic("-1.01")
        wrongDayTestLogic("-1.02")
        wrongDayTestLogic("-1.03")
        wrongDayTestLogic("-1.04")
        wrongDayTestLogic("-1.05")
        wrongDayTestLogic("-1.06")
        wrongDayTestLogic("-1.07")
        wrongDayTestLogic("-1.08")
        wrongDayTestLogic("-1.09")
        wrongDayTestLogic("-1.10")
        wrongDayTestLogic("-1.11")
        wrongDayTestLogic("-1.12")
    }
    @Test
    fun validateDetectsWrongMonth() {
        wrongMonthTestLogic("01.-1")
        wrongMonthTestLogic("01.0")
        wrongMonthTestLogic("01.13")
        wrongMonthTestLogic("01.14")
    }
    @Test
    fun validateDetectsCorrectDayAndMonth() {
        correctValueTestLogic("31.01")
        correctValueTestLogic("31.03")
        correctValueTestLogic("31.05")
        correctValueTestLogic("31.07")
        correctValueTestLogic("31.08")
        correctValueTestLogic("31.10")
        correctValueTestLogic("31.12")
        correctValueTestLogic("01.01")
        correctValueTestLogic("01.02")
        correctValueTestLogic("01.03")
        correctValueTestLogic("01.04")
        correctValueTestLogic("01.05")
        correctValueTestLogic("01.06")
        correctValueTestLogic("01.07")
        correctValueTestLogic("01.08")
        correctValueTestLogic("01.09")
        correctValueTestLogic("01.10")
        correctValueTestLogic("01.11")
        correctValueTestLogic("01.12")
    }
    private fun correctValueTestLogic(pvalue: String) {
        val data = Properties()
        data["someParam"] = pvalue
        val res = DayOfMonthValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isTrue()
        Assertions.assertThat(res.message).isEqualTo("")
    }
    private fun wrongDayTestLogic(pvalue: String) {
        val data = Properties()
        data["someParam"] = pvalue
        val res = DayOfMonthValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo("Invalid day in parameter 'someParam' value ('$pvalue')")
    }
    private fun wrongMonthTestLogic(pval: String) {
        val data = Properties()
        data["someParam"] = pval
        val res = DayOfMonthValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo(
                "Invalid month in parameter 'someParam' value ('$pval')"
        )
    }
}