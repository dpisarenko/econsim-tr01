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
class CopiesOfSoftwareAtTargetAudienceColFunctionTests {
    @Test
    fun invoke() {
        val out = CopiesOfSoftwareAtTargetAudienceColFunction()
        val prolog = "resourceLevel(60307200, 'list', 'r5', 123.45).".toPrologTheory()
        val act = out.invoke(prolog, 60307200L)
        act.shouldBe("123.45")
    }
}
