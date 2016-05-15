package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.junit.Test
import java.util.*

/**
 * Created by pisarenko on 15.05.2016.
 */
class NonZeroPositiveDoubleValueValidatorTests {
    @Test
    fun validateDetectsInvalidNumber() {
        invalidNumberTestLogic("abc")
        invalidNumberTestLogic("-")
        invalidNumberTestLogic("ß`´&/)")
    }

    private fun invalidNumberTestLogic(paramValue: String) {
        val data = Properties()
        data["someParam"] = paramValue
        val res = NonZeroPositiveDoubleValueValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo("Value of 'someParam' is equal to '$paramValue', which isn't numeric")
    }

    @Test
    fun validateDetectsZeroValue() {
        zeroValueTestLogic("0")
        zeroValueTestLogic("0.")
        zeroValueTestLogic("0.0")
        zeroValueTestLogic("0.0000")
        zeroValueTestLogic("00000.0")
    }

    private fun zeroValueTestLogic(paramValue: String) {
        val data = Properties()
        data["someParam"] = paramValue
        val res = NonZeroPositiveDoubleValueValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo("Value of 'someParam' is zero")
    }

    @Test
    fun validateDetectsNegativeValue() {
        negativeValueTestLogic("-1", "Value of 'someParam' is negative (-1.0)")
        negativeValueTestLogic("-0.000001", "Value of 'someParam' is negative (-1.0E-6)")
        negativeValueTestLogic("-20.3", "Value of 'someParam' is negative (-20.3)")
    }

    private fun negativeValueTestLogic(paramValue: String, expectedMessage: String) {
        val data = Properties()
        data["someParam"] = paramValue
        val res = NonZeroPositiveDoubleValueValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo(expectedMessage)
    }

    @Test
    fun validateDetectsNonZeroPositiveValue() {
        correctValueTestLogic("+10")
        correctValueTestLogic("10.0")
        correctValueTestLogic("0.000001")
        correctValueTestLogic("+10.0")
        correctValueTestLogic("+0.000001")
    }

    private fun correctValueTestLogic(paramValue: String) {
        val data = Properties()
        data["someParam"] = paramValue
        val res = NonZeroPositiveDoubleValueValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isTrue()
        Assertions.assertThat(res.message).isEqualTo("")
    }
}