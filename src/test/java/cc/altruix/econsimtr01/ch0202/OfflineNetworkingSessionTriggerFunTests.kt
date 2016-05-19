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
                population = Population(10),
                recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                timePerOfflineNetworkingSessions = 3.0
        )
        val out = OfflineNetworkingSessionTriggerFun(protagonist,
                3)
        val t = 0L.millisToSimulationDateTime()
        Assertions.assertThat(t.isBusinessDay()).isFalse()
        Assertions.assertThat(out.invoke(t)).isFalse()
    }
    @Test
    fun invokeReturnsFalseWhenDailyLimitExceeded() {
        dailyLimitExceededTestLogic(3)
        dailyLimitExceededTestLogic(4)
    }
    private fun dailyLimitExceededTestLogic(sessionsHeldDuringCurrentDay: Int) {
        val protagonist = Protagonist(
                availableTimePerWeek = 40,
                maxNetworkingSessionsPerBusinessDay = 3,
                population = Population(10),
                recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                timePerOfflineNetworkingSessions = 3.0
        )
        val out = OfflineNetworkingSessionTriggerFun(protagonist,
                3)
        val t = 0L.millisToSimulationDateTime().plusDays(2)
        Assertions.assertThat(t.isBusinessDay()).isTrue()
        protagonist.offlineNetworkingSessionsHeldDuringCurrentDay =
                sessionsHeldDuringCurrentDay
        Assertions.assertThat(out.invoke(t)).isFalse()
    }
    @Test
    fun invokeReturnsTrueIfBusinessDayAndDailyLimitNotExceeded() {
        val protagonist = Protagonist(
                availableTimePerWeek = 40,
                maxNetworkingSessionsPerBusinessDay = 3,
                timePerOfflineNetworkingSessions = 3.0,
                recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                population = Population(10)
        )
        val out = OfflineNetworkingSessionTriggerFun(protagonist,
                3)
        val t = 0L.millisToSimulationDateTime().plusDays(2)
        Assertions.assertThat(t.isBusinessDay()).isTrue()
        protagonist.offlineNetworkingSessionsHeldDuringCurrentDay =
                2
        Assertions.assertThat(out.invoke(t)).isTrue()
    }
}
