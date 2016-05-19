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

import cc.altruix.econsimtr01.shouldBe
import cc.altruix.econsimtr01.toPrologTheory
import org.junit.Test

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class TimeLongFormColFunctionTests {
    @Test
    fun invoke() {
        val prolog = "measurementTime(172800, '0000-01-03 00:00').".toPrologTheory()
        val act = TimeLongFormColFunction().invoke(prolog, 172800L)
        act.shouldBe("0000-01-03 00:00")
    }
}
