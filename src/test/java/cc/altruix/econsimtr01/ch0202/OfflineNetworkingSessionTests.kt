/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.millisToSimulationDateTime
import cc.altruix.econsimtr01.mock
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class OfflineNetworkingSessionTests {
    @Test
    fun runDoesntDoAnythingIfValidateReturnsFalse() {
        // Prepare
        val population = Population(100)
        val agent = Mockito.spy(
                Protagonist(
                        availableTimePerWeek = 40,
                        maxNetworkingSessionsPerBusinessDay = 3,
                        timePerOfflineNetworkingSessions = 3.0,
                        recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                        willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                        population = population
                )
        )
        val out = Mockito.spy(
                OfflineNetworkingSession(
                        agent = agent,
                        maxNetworkingSessionsPerBusinessDay = 3,
                        timePerOfflineNetworkingSession = 3.0,
                        recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                        willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                        population = population
                )
        )
        val t = 0L.millisToSimulationDateTime()
        val meetingPartner = Person()
        Mockito.doReturn(meetingPartner).`when`(out).findMeetingPartner()
        Mockito.doReturn(false).`when`(out).validate()
        agent.offlineNetworkingSessionsHeldDuringCurrentDay = 1
        // Run method under test
        out.run(t)
        // Verify
        Mockito.verify(out, Mockito.never()).findMeetingPartner()
        Assertions.assertThat(agent.offlineNetworkingSessionsHeldDuringCurrentDay).isEqualTo(1)
        Mockito.verify(agent, Mockito.never()).remove(Sim1ParametersProvider.RESOURCE_AVAILABLE_TIME.id,
                3.0)
        Mockito.verify(out, Mockito.never()).updateWillingnessToRecommend(meetingPartner)
        Mockito.verify(out, Mockito.never()).updateWillingnessToPurchase(meetingPartner)
    }
    @Test
    fun runDoesntDoAnythingIfNoMeetingPartnerIsAvailable() {
        // Prepare
        val population = Population(100)
        val agent = Mockito.spy(
                Protagonist(
                        availableTimePerWeek = 40,
                        maxNetworkingSessionsPerBusinessDay = 3,
                        timePerOfflineNetworkingSessions = 3.0,
                        recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                        willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                        population = population
                )
        )
        val out = Mockito.spy(
                OfflineNetworkingSession(
                        agent = agent,
                        maxNetworkingSessionsPerBusinessDay = 3,
                        timePerOfflineNetworkingSession = 3.0,
                        recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                        willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                        population = population
                )
        )
        val t = 0L.millisToSimulationDateTime()
        val meetingPartner = null
        Mockito.doReturn(meetingPartner).`when`(out).findMeetingPartner()
        Mockito.doReturn(true).`when`(out).validate()
        agent.offlineNetworkingSessionsHeldDuringCurrentDay = 1
        // Run method under test
        out.run(t)
        // Verify
        Mockito.verify(out).findMeetingPartner()
        Assertions.assertThat(agent.offlineNetworkingSessionsHeldDuringCurrentDay).isEqualTo(1)
        Mockito.verify(agent, Mockito.never()).remove(Sim1ParametersProvider.RESOURCE_AVAILABLE_TIME.id,
                3.0)
    }
    @Test
    fun runDefaultScenario() {
        // Prepare
        val population = Population(100)
        val agent = Mockito.spy(
                Protagonist(
                        availableTimePerWeek = 40,
                        maxNetworkingSessionsPerBusinessDay = 3,
                        timePerOfflineNetworkingSessions = 3.0,
                        recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                        willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                        population = population
                )
        )
        val out = Mockito.spy(
                OfflineNetworkingSession(
                        agent = agent,
                        maxNetworkingSessionsPerBusinessDay = 3,
                        timePerOfflineNetworkingSession = 3.0,
                        recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                        willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                        population = population
                )
        )
        val t = 0L.millisToSimulationDateTime()
        val meetingPartner = Person()
        Mockito.doReturn(meetingPartner).`when`(out).findMeetingPartner()
        Mockito.doReturn(true).`when`(out).validate()
        agent.offlineNetworkingSessionsHeldDuringCurrentDay = 1
        Mockito.doNothing().`when`(out).updateWillingnessToPurchase(meetingPartner)
        Mockito.doNothing().`when`(out).updateWillingnessToRecommend(meetingPartner)
        Assertions.assertThat(meetingPartner.offlineNetworkingSessionHeld).isFalse()
        // Run method under test
        out.run(t)
        // Verify
        Mockito.verify(out).findMeetingPartner()
        Assertions.assertThat(agent.offlineNetworkingSessionsHeldDuringCurrentDay).isEqualTo(2)
        Mockito.verify(agent).remove(Sim1ParametersProvider.RESOURCE_AVAILABLE_TIME.id,
                3.0)
        Mockito.verify(out).updateWillingnessToRecommend(meetingPartner)
        Mockito.verify(out).updateWillingnessToPurchase(meetingPartner)
        Assertions.assertThat(meetingPartner.offlineNetworkingSessionHeld).isTrue()
    }
    @Test
    fun updateWillingnessToPurchaseDefaultScenario() {
        updateWillingnessToPurchaseTestLogic(true, true)
        updateWillingnessToPurchaseTestLogic(false, false)
    }
    private fun updateWillingnessToPurchaseTestLogic(experimentResult: Boolean,
                                                     expectedResult:Boolean) {
        // Prepare
        val population = Population(100)
        val agent = Mockito.spy(
                Protagonist(
                        availableTimePerWeek = 40,
                        maxNetworkingSessionsPerBusinessDay = 3,
                        timePerOfflineNetworkingSessions = 3.0,
                        recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                        willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                        population = population
                )
        )
        val out = Mockito.spy(
                OfflineNetworkingSession(
                        agent = agent,
                        maxNetworkingSessionsPerBusinessDay = 3,
                        timePerOfflineNetworkingSession = 3.0,
                        recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                        willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                        population = population
                )
        )
        val meetingPartner = Person()
        Assertions.assertThat(meetingPartner.willingToPurchase).isFalse()
        Mockito.doReturn(experimentResult).`when`(out).experiment(out.willingnessToPurchaseConversion)
        // Run method under test
        out.updateWillingnessToPurchase(meetingPartner)
        // Verify
        Mockito.verify(out).experiment(out.willingnessToPurchaseConversion)
        Assertions.assertThat(meetingPartner.willingToPurchase).isEqualTo(expectedResult)
    }
    @Test
    fun updateWillingnessToRecommendDefaultScenario() {
        updateWillingnessToRecommendTestLogic(true, true)
        updateWillingnessToRecommendTestLogic(false, false)
    }
    private fun updateWillingnessToRecommendTestLogic(experimentResult: Boolean,
                                                      expectedFlagValue: Boolean) {
        // Prepare
        val population = Population(100)
        val agent = Mockito.spy(
                Protagonist(
                        availableTimePerWeek = 40,
                        maxNetworkingSessionsPerBusinessDay = 3,
                        timePerOfflineNetworkingSessions = 3.0,
                        recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                        willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                        population = population
                )
        )
        val out = Mockito.spy(
                OfflineNetworkingSession(
                        agent = agent,
                        maxNetworkingSessionsPerBusinessDay = 3,
                        timePerOfflineNetworkingSession = 3.0,
                        recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                        willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                        population = population
                )
        )
        val meetingPartner = Person()
        Assertions.assertThat(meetingPartner.willingToRecommend).isFalse()
        Mockito.doReturn(experimentResult).`when`(out).experiment(out.willingnessToPurchaseConversion)
        // Run method under test
        out.updateWillingnessToRecommend(meetingPartner)
        // Verify
        Mockito.verify(out).experiment(out.willingnessToPurchaseConversion)
        Assertions.assertThat(meetingPartner.willingToRecommend).isEqualTo(expectedFlagValue)
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
                recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                population = population
        )
        val out = OfflineNetworkingSession(agent = agent,
                maxNetworkingSessionsPerBusinessDay = 3,
                timePerOfflineNetworkingSession = 3.0,
                recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
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
                recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                population = population
        )
        agent.offlineNetworkingSessionsHeldDuringCurrentDay = 2
        agent.put(
                Sim1ParametersProvider.RESOURCE_AVAILABLE_TIME.id,
                availableTime
        )
        Assertions
                .assertThat(agent.amount(Sim1ParametersProvider.RESOURCE_AVAILABLE_TIME.id))
                .isEqualTo(availableTime)
        val out = OfflineNetworkingSession(agent = agent,
                maxNetworkingSessionsPerBusinessDay = 3,
                timePerOfflineNetworkingSession = 3.0,
                recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                population = population
        )
        Assertions.assertThat(out.validate()).isFalse()
    }
    @Test
    fun validateReturnsTrueIfEverythingOk() {
        val population = Population(100)
        val agent = Protagonist(
                availableTimePerWeek = 40,
                maxNetworkingSessionsPerBusinessDay = 2,
                timePerOfflineNetworkingSessions = 3.0,
                recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                population = population
        )
        agent.offlineNetworkingSessionsHeldDuringCurrentDay = 2
        agent.put(
                Sim1ParametersProvider.RESOURCE_AVAILABLE_TIME.id,
                3.0
        )
        Assertions
                .assertThat(agent.amount(Sim1ParametersProvider.RESOURCE_AVAILABLE_TIME.id))
                .isEqualTo(3.0)
        val out = OfflineNetworkingSession(agent = agent,
                maxNetworkingSessionsPerBusinessDay = 3,
                timePerOfflineNetworkingSession = 3.0,
                recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                population = population
        )
        Assertions.assertThat(out.validate()).isTrue()
    }
    @Test
    fun findMeetingPartnerDefaultScenario() {
        // Prepare
        val person1 = createPotentialMeetingPartner(false, false)
        val person2 = createPotentialMeetingPartner(true, false)
        val person3 = createPotentialMeetingPartner(false, true)
        val person4 = createPotentialMeetingPartner(true, true)
        val people = arrayListOf(person1, person2, person3, person4)
        val population = mock<IPopulation>()
        Mockito.`when`(population.people()).thenReturn(people)
        val agent = Protagonist(
                availableTimePerWeek = 40,
                maxNetworkingSessionsPerBusinessDay = 2,
                timePerOfflineNetworkingSessions = 3.0,
                recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                population = population
        )
        val out = OfflineNetworkingSession(agent = agent,
                maxNetworkingSessionsPerBusinessDay = 3,
                timePerOfflineNetworkingSession = 3.0,
                recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                population = population
        )
        // Run method under test
        val meetingPartner = out.findMeetingPartner()
        // Verify
        Assertions.assertThat(meetingPartner).isNotNull
        Assertions.assertThat(meetingPartner).isSameAs(person2)
    }

    private fun createPotentialMeetingPartner(willingToMeet: Boolean,
                                              offlineNetworkingSessionHeld: Boolean): Person {
        val person = Person()
        person.willingToMeet = willingToMeet
        person.offlineNetworkingSessionHeld = offlineNetworkingSessionHeld
        return person
    }
}
