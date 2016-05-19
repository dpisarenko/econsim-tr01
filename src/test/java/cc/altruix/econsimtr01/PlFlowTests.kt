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

import cc.altruix.econsimtr01.ch0201.After
import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * Created by pisarenko on 14.04.2016.
 */
class PlFlowTests {
    @Test
    fun addFlowFiresUpFollowingTriggers() {
        val out = PlFlow(
                "id",
                "src",
                "target",
                "resource",
                null,
                { x -> true }
        )
        val after1 = Mockito.spy(After("f1"))
        val after2 = Mockito.spy(After("f2"))

        out.addFollowUpFlow(after1)
        out.addFollowUpFlow(after2)

        val time = DateTime(2016, 4, 14, 13, 29, 0)

        out.flows = LinkedList<ResourceFlow>()

        // Run method under test
        out.addFlow(mock<IAgent>(), mock<IAgent>(), time)

        // Verify
        Mockito.verify(after1, Mockito.times(1)).updateNextFiringTime(time)
        Mockito.verify(after2, Mockito.times(1)).updateNextFiringTime(time)
    }

}