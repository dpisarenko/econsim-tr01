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
        val data = Properties()
        data["someParam"] = "abc"
        val res = NonZeroPositiveDoubleValueValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo("Value of 'someParam' is equal to 'abc', which isn't numeric")
    }
    @Test
    fun validateDetectsZeroValue() {

    }
    @Test
    fun validateDetectsNegativeValue() {

    }
    @Test
    fun validateDetectsNonZeroPositiveValue() {

    }

}