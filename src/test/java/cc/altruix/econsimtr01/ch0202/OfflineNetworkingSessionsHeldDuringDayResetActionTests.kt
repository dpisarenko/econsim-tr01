package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.millisToSimulationDateTime
import cc.altruix.econsimtr01.mock
import org.fest.assertions.Assertions
import org.junit.Test

/**
 * Created by pisarenko on 04.05.2016.
 */
class OfflineNetworkingSessionsHeldDuringDayResetActionTests {
    @Test
    fun run() {
        // Prepare
        val agent = Protagonist(
                availableTimePerWeek = 40,
                maxNetworkingSessionsPerBusinessDay = 2,
                timePerOfflineNetworkingSessions = 3.0,
                recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                population = mock<IPopulation>()
        )
        val out = OfflineNetworkingSessionsHeldDuringDayResetAction(
                agent,
                {true}
        )
        agent.offlineNetworkingSessionsHeldDuringCurrentDay = 42
        // Run method under test
        out.run(0L.millisToSimulationDateTime())
        // Verify
        Assertions.assertThat(agent.offlineNetworkingSessionsHeldDuringCurrentDay).isEqualTo(0)
    }
}