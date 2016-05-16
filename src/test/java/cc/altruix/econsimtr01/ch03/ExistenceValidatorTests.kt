/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.junit.Test
import java.util.*

/**
 * Created by pisarenko on 15.05.2016.
 */
class ExistenceValidatorTests {
    @Test
    fun validateDetectsMissingParameter() {
        val data = Properties()
        val res = ExistenceValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo("Missing parameter 'someParam'")
    }
    @Test
    fun validateDetectsPresentParameter() {
        val data = Properties()
        data.put("someParam", "")
        val res = ExistenceValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isTrue()
        Assertions.assertThat(res.message).isEqualTo("")

    }
}