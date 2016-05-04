package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.millisToSimulationDateTime
import cc.altruix.econsimtr01.shouldBe
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
        val data = HashMap<Sim1aResultRowField,Double>()
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
        Assertions.assertThat(resultsStorage.get(t)).isNotNull
        Assertions.assertThat(resultsStorage.get(t)?.data).isNotNull
        Assertions.assertThat(resultsStorage.get(t)?.data?.get(scenarioName)).isNotNull
        resultsStorage.get(t)
                ?.data
                ?.get(scenarioName)
                ?.get(Sim1aResultRowField.PEOPLE_WILLING_TO_MEET)
                ?.shouldBe(peopleWillingToMeet)
        resultsStorage.get(t)
                ?.data
                ?.get(scenarioName)
                ?.get(Sim1aResultRowField.PEOPLE_WILLING_TO_RECOMMEND)
                ?.shouldBe(peopleWillingToRecommend)
        resultsStorage.get(t)
                ?.data
                ?.get(scenarioName)
                ?.get(Sim1aResultRowField.PEOPLE_MET)
                ?.shouldBe(peopleMet)
        resultsStorage.get(t)
                ?.data
                ?.get(scenarioName)
                ?.get(Sim1aResultRowField.PEOPLE_WILLING_TO_PURCHASE)
                ?.shouldBe(peopleWillingToPurchase)

    }
}