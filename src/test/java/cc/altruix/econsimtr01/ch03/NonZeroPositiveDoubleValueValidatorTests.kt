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
        Assertions.assertThat(res.message).isEqualTo("Value of 'someParam' zero")
    }

    @Test
    fun validateDetectsNegativeValue() {

    }
    @Test
    fun validateDetectsNonZeroPositiveValue() {

    }

}