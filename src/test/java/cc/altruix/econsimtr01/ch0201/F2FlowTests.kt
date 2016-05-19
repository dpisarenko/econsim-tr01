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

import cc.altruix.econsimtr01.millisToSimulationDateTime
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class F2FlowTests {
    @Test
    fun runSunnyDay() {
        val out = createObjectUnderTest()
        out.list = ListAgent("list")
        out.agents = emptyList()
        val t = 0L.millisToSimulationDateTime()
        val priceOfSoftwareSoldToNewlyActivatedAudience = 10.35
        Mockito.doReturn(priceOfSoftwareSoldToNewlyActivatedAudience)
            .`when`(out).calculatePriceOfSoftwareSold()
        // Run method under test
        out.run(t)
        // Verify
        Mockito.verify(out).run(priceOfSoftwareSoldToNewlyActivatedAudience, t)
    }

    @Test
    fun calculatePriceOfSoftwareSoldSunnyDay() {
        val out = createObjectUnderTest()
        val list = Mockito.spy(ListAgent("list"))
        list.buyersCount = 321
        out.list = list
        // Run method under test
        val act = out.calculatePriceOfSoftwareSold()
        // Verify
        Assertions.assertThat(act).isEqualTo(321 * 23.45)
    }

    private fun createObjectUnderTest(): F2Flow {
        return Mockito.spy(F2Flow("id",
                "src", "target", "res",
                null, { true }, 23.45))
    }
}
