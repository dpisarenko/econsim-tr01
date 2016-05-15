package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.junit.Test
import java.util.*

/**
 * Created by pisarenko on 15.05.2016.
 */
class NonBlankStringValidatorTests {
    @Test
    fun validateDetectsBlankString() {
        validateDetectsBlankStringTestLogic("")
        validateDetectsBlankStringTestLogic(" ")
        validateDetectsBlankStringTestLogic("   ")
        validateDetectsBlankStringTestLogic("\t")
    }

    private fun validateDetectsBlankStringTestLogic(paramValue: String) {
        val data = Properties()
        data["someParam"] = paramValue
        val res = NonBlankStringValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo("Value of parameter 'someParam' is blank")
    }

    @Test
    fun validateDetectsNonBlankString() {
        val data = Properties()
        data["someParam"] = "bla"
        val res = NonBlankStringValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isTrue()
        Assertions.assertThat(res.message).isEqualTo("")
    }
}