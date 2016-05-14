package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.junit.Test

/**
 * Created by pisarenko on 14.05.2016.
 */
class CmdLineParametersValidatorTests {
    @Test
    fun validateReturnsFalseOnEmptyArray() {
        val out = CmdLineParametersValidator()
        val res = out.validate(emptyArray())
        Assertions.assertThat(res).isNotNull
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo(CmdLineParametersValidator.USAGE)
    }
}