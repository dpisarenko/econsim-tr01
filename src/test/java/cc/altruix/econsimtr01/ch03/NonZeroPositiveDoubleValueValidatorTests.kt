/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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