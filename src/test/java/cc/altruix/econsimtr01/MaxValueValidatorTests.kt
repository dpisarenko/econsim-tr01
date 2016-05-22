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

package cc.altruix.econsimtr01

import org.fest.assertions.Assertions
import org.junit.Test
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class MaxValueValidatorTests {
    @Test
    fun validateDetectsInvalidData() {
        // Prepare
        val data = Properties()
        data["param"] = "2.0"
        val out = MaxValueValidator(1.0)
        // Run method under test
        val res = out.validate(data, "param")
        // Verify
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo("Max. allowed value of " +
            "parameter 'param' is 1.0, but actual value is equal to 2.0")
    }
    @Test
    fun validateDetectsValidData() {
        // Prepare
        val data = Properties()
        data["param"] = "1.0"
        val out = MaxValueValidator(1.0)
        // Run method under test
        val res = out.validate(data, "param")
        // Verify
        Assertions.assertThat(res.valid).isTrue()
        Assertions.assertThat(res.message).isEqualTo("")
    }
}
