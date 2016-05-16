/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.shouldBe
import org.junit.Test

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class TimeSecondsColFunctionTests {
    @Test
    fun invoke() {
        val prolog = Prolog()
        val act = TimeSecondsColFunction().invoke(prolog, 1L)
        act.shouldBe("1")
    }
}
