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
