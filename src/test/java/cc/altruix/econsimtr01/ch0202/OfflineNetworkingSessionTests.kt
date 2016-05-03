package cc.altruix.econsimtr01.ch0202

import org.fest.assertions.Assertions
import org.junit.Test

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class OfflineNetworkingSessionTests {
    @Test
    fun doesntDoAnythingIfValidateReturnsFalse() {
        // TODO: Implement this
    }

    @Test
    fun validateDetectsDailyLimitExceeded() {
        val population = Population(100)
        val agent = Protagonist(
                availableTimePerWeek = 40,
                maxNetworkingSessionsPerBusinessDay = 3,
                timePerOfflineNetworkingSessions = 3.0,
                population = population
        )
        val out = OfflineNetworkingSession(agent = agent,
                maxNetworkingSessionsPerBusinessDay = 3,
                timePerOfflineNetworkingSession = 3.0,
                population = population)
        agent.offlineNetworkingSessionsHeldDuringCurrentDay = 3
        Assertions.assertThat(out.validate()).isFalse()
    }
    @Test
    fun validateDetectsAgentHasTooLittleTimeLeft() {
        // TODO: Implement this
    }
    @Test
    fun validateReturnsTrueIfEverythingOk() {
        // TODO: Implement this
    }
}
