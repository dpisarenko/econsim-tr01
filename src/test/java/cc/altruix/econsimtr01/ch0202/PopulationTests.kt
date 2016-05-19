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
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class PopulationTests {
    @Test
    fun initCreatesPeopleWillingToRecommend() {
        val initialNetworkSize = 10
        val out = Population(initialNetworkSize)
        Assertions.assertThat(out.people).isNotNull
        Assertions.assertThat(out.people.size).isEqualTo(initialNetworkSize)
        out.people.forEach {
            Assertions.assertThat(it.willingToRecommend).isTrue()
        }
    }
}
