/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
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