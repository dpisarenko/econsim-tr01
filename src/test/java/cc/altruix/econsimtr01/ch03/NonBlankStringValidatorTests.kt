/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 */

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