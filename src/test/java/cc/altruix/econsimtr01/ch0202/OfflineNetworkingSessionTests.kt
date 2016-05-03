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
        dailyLimitDetectionTestLogic(3)
        dailyLimitDetectionTestLogic(4)
        dailyLimitDetectionTestLogic(5)
    }

    private fun dailyLimitDetectionTestLogic(sessionsAlreadyHeldToday: Int) {
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
        agent.offlineNetworkingSessionsHeldDuringCurrentDay = sessionsAlreadyHeldToday
        Assertions.assertThat(out.validate()).isFalse()
    }

    @Test
    fun validateDetectsAgentHasTooLittleTimeLeft() {
        agentHasTooLittleTimeLeftTestLogic(1.0)
        agentHasTooLittleTimeLeftTestLogic(2.0)
        agentHasTooLittleTimeLeftTestLogic(2.99)
    }

    private fun agentHasTooLittleTimeLeftTestLogic(availableTime: Double) {
        val population = Population(100)
        val agent = Protagonist(
                availableTimePerWeek = 40,
                maxNetworkingSessionsPerBusinessDay = 2,
                timePerOfflineNetworkingSessions = 3.0,
                population = population
        )
        agent.offlineNetworkingSessionsHeldDuringCurrentDay = 2
        agent.put(
                Sim1.RESOURCE_AVAILABLE_TIME.id,
                availableTime
        )
        Assertions
                .assertThat(agent.amount(Sim1.RESOURCE_AVAILABLE_TIME.id))
                .isEqualTo(availableTime)
        val out = OfflineNetworkingSession(agent = agent,
                maxNetworkingSessionsPerBusinessDay = 3,
                timePerOfflineNetworkingSession = 3.0,
                population = population
        )
        Assertions.assertThat(out.validate()).isFalse()
    }

    @Test
    fun validateReturnsTrueIfEverythingOk() {
        // TODO: Implement this
    }
}
