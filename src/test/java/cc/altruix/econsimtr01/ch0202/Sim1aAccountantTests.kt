package cc.altruix.econsimtr01.ch0202

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
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow>()
        val scenarioName = "Scenario 1"
        val out = Mockito.spy(Sim1aAccountant(resultsStorage, scenarioName))
        val t = 0L.millisToSimulationDateTime()
        val row = Sim1aResultsRow(t)
        Mockito.doReturn(row).`when`(out).findOrCreateRow(resultsStorage, t)
        val data = mock<MutableMap<Sim1aResultRowField,Double>>()
        Mockito.doReturn(data).`when`(out).findOrCreateDataMap(row, scenarioName)
        val peopleWillingToMeet = 1.0
        val peopleWillingToRecommend = 2.0
        val peopleMet = 3.0
        val peopleWillingToPurchase = 4.0
        Mockito.doReturn(peopleWillingToMeet).`when`(out).calculatePeopleWillingToMeet(t)
        Mockito.doReturn(peopleWillingToRecommend).`when`(out).calculatePeopleWillingToRecommend(t)
        Mockito.doReturn(peopleMet).`when`(out).calculatePeopleMet(t)
        Mockito.doReturn(peopleWillingToPurchase).`when`(out).calculatePeopleWillingToPurchase(t)
        // Run method under test
        out.measure(t)
        // Verify
        Mockito.verify(out).findOrCreateRow(resultsStorage, t)
        Mockito.verify(out).findOrCreateDataMap(row, scenarioName)
        Mockito.verify(out).calculatePeopleWillingToMeet(t)
        Mockito.verify(out).calculatePeopleWillingToRecommend(t)
        Mockito.verify(out).calculatePeopleMet(t)
        Mockito.verify(out).calculatePeopleWillingToPurchase(t)
        Mockito.verify(data).put(Sim1aResultRowField.PEOPLE_WILLING_TO_MEET, peopleWillingToMeet)
        Mockito.verify(data).put(Sim1aResultRowField.PEOPLE_WILLING_TO_RECOMMEND, peopleWillingToRecommend)
        Mockito.verify(data).put(Sim1aResultRowField.PEOPLE_MET, peopleMet)
        Mockito.verify(data).put(Sim1aResultRowField.PEOPLE_WILLING_TO_PURCHASE, peopleWillingToPurchase)
    }
    @Test
    fun findOrCreateDataMapFindsDataMap() {
        // Prepare
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow>()
        val scenarioName = "Scenario 1"
        val out = Sim1aAccountant(resultsStorage, scenarioName)
        val dataMap = HashMap<Sim1aResultRowField, Double>()
        val t = 0L.millisToSimulationDateTime()
        val row = Sim1aResultsRow(t)
        row.data.put(scenarioName, dataMap)
        // Run method under test
        val actResult = out.findOrCreateDataMap(row, scenarioName)
        // Verify
        Assertions.assertThat(actResult).isSameAs(dataMap)
    }
    @Test
    fun findOrCreateDataMapCreatesDataMap() {
        // Prepare
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow>()
        val scenarioName = "Scenario 1"
        val out = Sim1aAccountant(resultsStorage, scenarioName)
        val dataMap = HashMap<Sim1aResultRowField, Double>()
        val t = 0L.millisToSimulationDateTime()
        val row = Sim1aResultsRow(t)
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
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow>()
        val scenarioName = "Scenario 1"
        val t = 0L.millisToSimulationDateTime()
        val out = Sim1aAccountant(resultsStorage, scenarioName)
        val row = Sim1aResultsRow(t)
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
        val resultsStorage = HashMap<DateTime, Sim1aResultsRow>()
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
}