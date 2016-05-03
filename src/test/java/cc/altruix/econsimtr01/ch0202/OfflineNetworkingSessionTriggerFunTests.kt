package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.isBusinessDay
import cc.altruix.econsimtr01.millisToSimulationDateTime
import org.fest.assertions.Assertions
import org.junit.Test

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class OfflineNetworkingSessionTriggerFunTests {
    @Test
    fun invokeReturnsFalseOnNonBusinessDays() {
        val protagonist = Protagonist(
                availableTimePerWeek = 40,
                maxNetworkingSessionsPerBusinessDay = 3,
                population = Population(10)
        )
        val out = OfflineNetworkingSessionTriggerFun(protagonist,
                3)
        val t = 0L.millisToSimulationDateTime()
        Assertions.assertThat(t.isBusinessDay()).isFalse()
        Assertions.assertThat(out.invoke(t)).isFalse()
    }

}
