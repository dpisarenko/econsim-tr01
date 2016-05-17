/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.millisToSimulationDateTime
import cc.altruix.econsimtr01.mock
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * Created by pisarenko on 04.05.2016.
 */
class Sim1aAccountantTests {
    @Test
    fun measureWiring() {
        // Prepare
        val resultsStorage = HashMap<DateTime,
                Sim1aResultsRow<Sim1aResultRowField>>()
        val scenarioName = "Scenario 1"
        val out = Mockito.spy(Sim1aAccountant(resultsStorage, scenarioName))
        val t = 0L.millisToSimulationDateTime()
        val row = Sim1aResultsRow<Sim1aResultRowField>(t)
        Mockito.doReturn(row).`when`(out).findOrCreateRow(resultsStorage, t)
        val data = mock<MutableMap<Sim1aResultRowField,Double>>()
        Mockito.doReturn(data).`when`(out).findOrCreateDataMap(row, scenarioName)
        val peopleWillingToMeet = 1.0
        val peopleWillingToRecommend = 2.0
        val peopleMet = 3.0
        val peopleWillingToPurchase = 4.0
        val population = mock<IPopulation>()
        val protagonist = createProtagonist(population)
        val agents = emptyList<IAgent>()
        Mockito.doReturn(protagonist).`when`(out).findProtagonist(agents)
        Mockito.doReturn(peopleWillingToMeet).`when`(out).calculatePeopleWillingToMeet(t, population)
        Mockito.doReturn(peopleWillingToRecommend).`when`(out).calculatePeopleWillingToRecommend(t, population)
        Mockito.doReturn(peopleMet).`when`(out).calculatePeopleMet(t, population)
        Mockito.doReturn(peopleWillingToPurchase).`when`(out).calculatePeopleWillingToPurchase(t, population)
        // Run method under test
        out.measure(t, agents)
        // Verify
        Mockito.verify(out).findProtagonist(agents)
        Mockito.verify(out).findOrCreateRow(resultsStorage, t)
        Mockito.verify(out).findOrCreateDataMap(row, scenarioName)
        Mockito.verify(out).calculatePeopleWillingToMeet(t, population)
        Mockito.verify(out).calculatePeopleWillingToRecommend(t, population)
        Mockito.verify(out).calculatePeopleMet(t, population)
        Mockito.verify(out).calculatePeopleWillingToPurchase(t, population)
        Mockito.verify(data).put(Sim1aResultRowField.PEOPLE_WILLING_TO_MEET, peopleWillingToMeet)
        Mockito.verify(data).put(Sim1aResultRowField.PEOPLE_WILLING_TO_RECOMMEND, peopleWillingToRecommend)
        Mockito.verify(data).put(Sim1aResultRowField.PEOPLE_MET, peopleMet)
        Mockito.verify(data).put(Sim1aResultRowField.PEOPLE_WILLING_TO_PURCHASE, peopleWillingToPurchase)
    }

    private fun createProtagonist(population: IPopulation): Protagonist {
        return Protagonist(availableTimePerWeek = 40,
                maxNetworkingSessionsPerBusinessDay = Sim1ParametersProvider.MAX_NETWORKING_SESSIONS_PER_BUSINESS_DAY,
                timePerOfflineNetworkingSessions = Sim1ParametersProvider.TIME_PER_OFFLINE_NETWORKING_SESSION,
                recommendationConversion = Sim1ParametersProvider.RECOMMENDATION_CONVERSION,
                willingnessToPurchaseConversion = Sim1ParametersProvider.WILLINGNESS_TO_PURCHASE_CONVERSION,
                population = population)
    }

    @Test
    fun findOrCreateDataMapFindsDataMap() {
        // Prepare
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow<Sim1aResultRowField>>()
        val scenarioName = "Scenario 1"
        val out = Sim1aAccountant(resultsStorage, scenarioName)
        val dataMap = HashMap<Sim1aResultRowField, Double>()
        val t = 0L.millisToSimulationDateTime()
        val row = Sim1aResultsRow<Sim1aResultRowField>(t)
        row.data.put(scenarioName, dataMap)
        // Run method under test
        val actResult = out.findOrCreateDataMap(row, scenarioName)
        // Verify
        Assertions.assertThat(actResult).isSameAs(dataMap)
    }
    @Test
    fun findOrCreateDataMapCreatesDataMap() {
        // Prepare
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow<Sim1aResultRowField>>()
        val scenarioName = "Scenario 1"
        val out = Sim1aAccountant(resultsStorage, scenarioName)
        val dataMap = HashMap<Sim1aResultRowField, Double>()
        val t = 0L.millisToSimulationDateTime()
        val row = Sim1aResultsRow<Sim1aResultRowField>(t)
        row.data.put(scenarioName, dataMap)
        // Run method under test
        val actResult = out.findOrCreateDataMap(row, scenarioName)
        // Verify
        Assertions.assertThat(actResult).isNotNull
        Assertions.assertThat(actResult.size).isEqualTo(0)
        Assertions.assertThat(row.data.get(scenarioName)).isSameAs(actResult)
    }
    @Test
    fun findOrCreateRowFindsRow() {
        // Prepare
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow<Sim1aResultRowField>>()
        val scenarioName = "Scenario 1"
        val t = 0L.millisToSimulationDateTime()
        val out = Sim1aAccountant(resultsStorage, scenarioName)
        val row = Sim1aResultsRow<Sim1aResultRowField>(t)
        resultsStorage.put(t, row)
        // Run method under test
        val actResult = out.findOrCreateRow(resultsStorage, t)
        // Verify
        Assertions.assertThat(actResult).isSameAs(row)
        Assertions.assertThat(actResult.time).isEqualTo(t)
        Assertions.assertThat(actResult.data).isNotNull
        Assertions.assertThat(actResult.data).isEmpty()
    }
    @Test
    fun findOrCreateRowCreatesRow() {
        // Prepare
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow<Sim1aResultRowField>>()
        val scenarioName = "Scenario 1"
        val t = 0L.millisToSimulationDateTime()
        val out = Sim1aAccountant(resultsStorage, scenarioName)
        // Run method under test
        val actResult = out.findOrCreateRow(resultsStorage, t)
        // Verify
        Assertions.assertThat(actResult).isNotNull
        Assertions.assertThat(actResult.time).isEqualTo(t)
        Assertions.assertThat(actResult.data).isNotNull
        Assertions.assertThat(actResult.data).isEmpty()
    }
    @Test
    fun calculatePeopleWillingToPurchase() {
        // Prepare
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow<Sim1aResultRowField>>()
        val scenarioName = "Scenario 1"
        val t = 0L.millisToSimulationDateTime()
        val out = Sim1aAccountant(resultsStorage, scenarioName)
        val population = Population(0)
        // Run method under test and verify
        Assertions.assertThat(out.calculatePeopleWillingToPurchase(t, population)).isEqualTo(0.0)
        val person = Person()
        person.willingToPurchase = true
        population.people.add(person)
        Assertions.assertThat(out.calculatePeopleWillingToPurchase(t, population)).isEqualTo(1.0)
    }
    @Test
    fun calculatePeopleMet() {
        // Prepare
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow<Sim1aResultRowField>>()
        val scenarioName = "Scenario 1"
        val t = 0L.millisToSimulationDateTime()
        val out = Sim1aAccountant(resultsStorage, scenarioName)
        val population = Population(0)
        // Run method under test and verify
        Assertions.assertThat(out.calculatePeopleMet(t, population)).isEqualTo(0.0)
        val person = Person()
        person.offlineNetworkingSessionHeld = true
        population.people.add(person)
        Assertions.assertThat(out.calculatePeopleMet(t, population)).isEqualTo(1.0)
    }
    @Test
    fun calculatePeopleWillingToRecommend() {
        // Prepare
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow<Sim1aResultRowField>>()
        val scenarioName = "Scenario 1"
        val t = 0L.millisToSimulationDateTime()
        val out = Sim1aAccountant(resultsStorage, scenarioName)
        val population = Population(0)
        // Run method under test and verify
        Assertions.assertThat(out.calculatePeopleWillingToRecommend(t, population)).isEqualTo(0.0)
        val person = Person()
        person.willingToRecommend = true
        population.people.add(person)
        Assertions.assertThat(out.calculatePeopleWillingToRecommend(t, population)).isEqualTo(1.0)
    }
    @Test
    fun calculatePeopleWillingToMeet() {
        // Prepare
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow<Sim1aResultRowField>>()
        val scenarioName = "Scenario 1"
        val t = 0L.millisToSimulationDateTime()
        val out = Sim1aAccountant(resultsStorage, scenarioName)
        val population = Population(0)
        // Run method under test and verify
        Assertions.assertThat(out.calculatePeopleWillingToMeet(t, population)).isEqualTo(0.0)
        val person = Person()
        person.willingToMeet = true
        population.people.add(person)
        Assertions.assertThat(out.calculatePeopleWillingToMeet(t, population)).isEqualTo(1.0)
    }

    @Test
    fun findProtagonist() {
        // Prepare
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow<Sim1aResultRowField>>()
        val scenarioName = "Scenario 1"
        val t = 0L.millisToSimulationDateTime()
        val out = Sim1aAccountant(resultsStorage, scenarioName)
        val population = Population(0)
        val randomDick = mock<IAgent>()
        val protagonist = createProtagonist(population)
        val agents = listOf(randomDick, protagonist)
        // Run method under test
        val result = out.findProtagonist(agents)
        // Verify
        Assertions.assertThat(result).isSameAs(protagonist)
    }
}