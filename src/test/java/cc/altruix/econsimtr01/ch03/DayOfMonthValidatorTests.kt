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
class DayOfMonthValidatorTests {
    @Test
    fun validateDetectsInvalidFormat() {
        invalidFormatTestLogic("3008")
        invalidFormatTestLogic("30a.08")
        invalidFormatTestLogic("30.0v8")
        invalidFormatTestLogic("30!08")
        invalidFormatTestLogic("-1.01")
        invalidFormatTestLogic("-1.02")
        invalidFormatTestLogic("-1.03")
        invalidFormatTestLogic("-1.04")
        invalidFormatTestLogic("-1.05")
        invalidFormatTestLogic("-1.06")
        invalidFormatTestLogic("-1.07")
        invalidFormatTestLogic("-1.08")
        invalidFormatTestLogic("-1.09")
        invalidFormatTestLogic("-1.10")
        invalidFormatTestLogic("-1.11")
        invalidFormatTestLogic("-1.12")
        invalidFormatTestLogic("01.-1")
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
    }
    @Test
    fun validateDetectsWrongMonth() {
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
    private fun invalidFormatTestLogic(pval: String) {
        val data = Properties()
        data["someParam"] = pval
        val res = DayOfMonthValidator.validate(data, "someParam")
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo("Value '$pval' of 'someParam' has invalid format")
    }
}