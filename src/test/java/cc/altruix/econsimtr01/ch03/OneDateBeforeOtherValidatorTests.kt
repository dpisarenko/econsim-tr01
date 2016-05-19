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
 * Created by pisarenko on 16.05.2016.
 */
class OneDateBeforeOtherValidatorTests {
    @Test
    fun validate() {
        validateTestLogic("05.07", "10.08", true, "")
        validateTestLogic("10.08", "05.07", false, "Process2End " +
                "('10.08') must be before " +
                "Process3End ('05.07')")
    }

    private fun validateTestLogic(earlierDate: String,
                                  laterDate: String,
                                  expectedValidity: Boolean,
                                  expectedMessage: String) {
        // Prepare
        val data = Properties()
        data["Process2End"] = earlierDate
        data["Process3End"] = laterDate
        val scenario =
                PropertiesFileSimParametersProviderWithPredefinedData(data)
        scenario.initAndValidate()
        val out = OneDateBeforeOtherValidator("Process2End", "Process3End")
        // Run method under test
        val res = out.validate(scenario)
        // Verify
        Assertions.assertThat(res.message).isEqualTo(expectedMessage)
        Assertions.assertThat(res.valid).isEqualTo(expectedValidity)
    }
}