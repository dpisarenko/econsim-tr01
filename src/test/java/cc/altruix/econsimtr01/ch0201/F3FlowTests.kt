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

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.millisToSimulationDateTime
import org.junit.Test
import org.mockito.Mockito

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class F3FlowTests {
    @Test
    fun runSunnyDay() {
        val out = Mockito.spy(F3Flow("id",
                "src",
                "target",
                "resource",
                null,
                {false}))
        val list = ListAgent("list")
        list.buyersCount = 123
        out.list = list
        out.agents = emptyList()
        val t = 0L.millisToSimulationDateTime()
        // Run method under test
        out.run(t)
        // Verify
        Mockito.verify(out).run(123.0, t)
    }
}
