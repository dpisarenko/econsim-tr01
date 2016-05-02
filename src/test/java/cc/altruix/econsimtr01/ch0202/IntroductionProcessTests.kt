package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.millisToSimulationDateTime
import cc.altruix.econsimtr01.mock
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class IntroductionProcessTests {
    @Test
    fun run() {
        // Prepare
        val population = mock<IPopulation>();
        val out = Mockito.spy( IntroductionProcess(
                population,
                {true}
        ))
        val network = emptyList<Person>()
        Mockito.doReturn(network).`when`(out).getNetwork(population)
        val recommenders = emptyList<Person>()
        Mockito.doReturn(recommenders).`when`(out).getRecommenders(network)
        val person1 = Person()
        person1.willingToMeet = false
        val person2 = Person()
        person2.willingToMeet = false
        val leads = listOf(person1, person2)
        Mockito.doReturn(leads).`when`(out).recommend(recommenders)
        Assertions.assertThat(person1.willingToMeet).isFalse()
        Assertions.assertThat(person2.willingToMeet).isFalse()
        val personWillingToMeet1 = Person()
        val personWillingToMeet2 = Person()
        Mockito.doReturn(personWillingToMeet1)
                .doReturn(personWillingToMeet2)
                .`when`(out)
                .createPersonWillingToMeet()
        // Run method under test
        out.run(0L.millisToSimulationDateTime())
        // Verify
        Mockito.verify(out).getNetwork(population)
        Mockito.verify(out).getRecommenders(network)
        Mockito.verify(out).recommend(recommenders)
        Assertions.assertThat(person1.willingToMeet).isFalse()
        Assertions.assertThat(person2.willingToMeet).isFalse()
        Mockito.verify(out, Mockito.times(2)).createPersonWillingToMeet()
        Mockito.verify(population).addPerson(personWillingToMeet1)
        Mockito.verify(population).addPerson(personWillingToMeet2)
    }
    @Test
    fun getNetwork() {
        val networkMember = createPerson(true)
        val stranger = createPerson(false)
        val people = arrayListOf(networkMember, stranger)
        val population = mock<IPopulation>()
        Mockito.`when`(population.people())
                .thenReturn(people)
        val out = IntroductionProcess(
                population,
                { true }
        )
        // Run method under test
        val network = out.getNetwork(population)
        // Verify
        Assertions.assertThat(network).isNotNull
        Assertions.assertThat(network).containsOnly(networkMember)
    }

    @Test
    fun getRecommenders() {
        val population = mock<IPopulation>()
        val out = IntroductionProcess(
                population,
                { true },
                0.2
        )
        val peopleInNetwork = 10
        val network = ArrayList<Person>(peopleInNetwork)
        for (i in 1..peopleInNetwork) {
            network.add(Person())
        }
        val recommenders = out.getRecommenders(network)
        Assertions.assertThat(recommenders.size).isEqualTo(2)
        recommenders.forEach {
            Assertions.assertThat(network.contains(it)).isTrue()
        }
    }

    @Test
    fun recommend() {
        val population = mock<IPopulation>()
        val out = IntroductionProcess(
                population,
                { true },
                0.2,
                0.6
        )
        val recommendersCount = 10
        val recommenders = ArrayList<Person>(recommendersCount)
        for (i in 1..recommendersCount) {
            recommenders.add(Person())
        }
        val leads = out.recommend(recommenders)
        Assertions.assertThat(leads.size).isEqualTo(6)
        leads.forEach {
            Assertions.assertThat(recommenders.contains(it)).isTrue()
        }
    }

    private fun createPerson(willingToRecommend:Boolean):Person
    {
        val result = Person()
        result.willingToRecommend = willingToRecommend
        return result
    }
}
