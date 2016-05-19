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

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.shouldBe
import cc.altruix.econsimtr01.toPrologTheory
import org.junit.Test

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class TotalNumberOfSubscribersInListColFunctionTests {
    @Test
    fun invoke() {
        val prolog = """
        resourceLevel(86400, 'list', 'r06-pc1', 143).
        resourceLevel(86400, 'list', 'r07-pc2', 144).
        resourceLevel(86400, 'list', 'r08-pc3', 145).
        resourceLevel(86400, 'list', 'r09-pc4', 146).
        resourceLevel(86400, 'list', 'r10-pc5', 147).
        resourceLevel(86400, 'list', 'r11-pc6', 148).
        resourceLevel(86400, 'list', 'r12-pc7', 149).
        """.toPrologTheory()

        val out = TotalNumberOfSubscribersInListColFunction()
        out.invoke(prolog, 86400L).shouldBe((
                143.0 +
                        144 +
                        145 +
                        146 +
                        147 +
                        148 +
                        149).toString())
    }
}
