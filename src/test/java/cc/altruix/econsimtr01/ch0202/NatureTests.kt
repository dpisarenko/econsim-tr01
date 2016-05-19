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

package cc.altruix.econsimtr01.ch0202

import org.fest.assertions.Assertions
import org.junit.Test

/**
 * Created by pisarenko on 11.05.2016.
 */
class NatureTests {
    @Test
    fun initSetsInfiniteResourceLevel() {
        val out = Nature()
        Assertions.assertThat(out.isInfinite(Sim1ParametersProvider.RESOURCE_AVAILABLE_TIME.id))
    }
}