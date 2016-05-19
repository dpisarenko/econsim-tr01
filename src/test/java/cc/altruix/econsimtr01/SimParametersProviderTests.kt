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

package cc.altruix.econsimtr01

import cc.altruix.econsimtr01.ch0201.InfiniteResourceSupply
import cc.altruix.econsimtr01.ch0201.InitialResourceLevel
import org.fest.assertions.Assertions
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 05.04.2016.
 */
class SimParametersProviderTests {
    @Test
    fun sunnyDay() {
        val out = SimParametersProvider(
                File("src/test/resources/Simulation1.params.pl").readText(),
                Collections.emptyList<IAgent>(),
                Collections.emptyList<PlFlow>(),
                Collections.emptyList<InitialResourceLevel>(),
                Collections.emptyList<InfiniteResourceSupply>()
        )
        Assertions.assertThat(out.maxDaysWithoutFood).isEqualTo(30)
        Assertions.assertThat(out.initialAmountOfPotatoes).isEqualTo(90.0)
        Assertions.assertThat(out.dailyPotatoConsumption).isEqualTo(1.0)
    }
}