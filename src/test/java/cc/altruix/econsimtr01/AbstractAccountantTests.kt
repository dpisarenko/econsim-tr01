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

import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class AbstractAccountantTests {
    open class TestAccountant(logTarget:StringBuilder,
                         agents:List<IAgent>,
                         resources:List<PlResource>)
        : AbstractAccountant(logTarget, agents, resources) {
        override fun measure(time: DateTime, agents: List<IAgent>) {
        }
    }

    @Test
    fun writeResourceDataCallsConvertToPrologString() {
        val logTarget = StringBuilder()
        val agents = emptyList<IAgent>()
        val txt =
                "People, who were exposed to Stacy's writings 6 times"
        val resource = PlResource(
                "r11-pc2",
                txt,
                "People")
        val out = Mockito.spy(
                        TestAccountant(
                                logTarget,
                                agents,
                                listOf(resource)
                        )
                )
        Mockito.doReturn("foo").`when`(out).convertToPrologString(txt)
        // Run method under test
        out.writeResourceData()
        // Verify
        Mockito.verify(out).convertToPrologString(txt)
        logTarget.toString().shouldBe("resource(r11-pc2, \"foo\", \"People\")" +
                ".\n")
    }
    @Test
    fun convertToPrologStringEscapesApostrophes() {
        val txt =
                "People, who were exposed to Stacy's writings 6 times"
        val out = TestAccountant(
                StringBuilder(),
                emptyList<IAgent>(),
                emptyList<PlResource>()
        )
        // Run method under test
        val act = out.convertToPrologString(txt)
        // Verify
        act.shouldBe("People, who were exposed to Stacy''s writings 6 times")
    }
}
