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
        // Run method under test
        out.run(0L.millisToSimulationDateTime())
        // Verify
        Mockito.verify(out).getNetwork(population)
        Mockito.verify(out).getRecommenders(network)
        Mockito.verify(out).recommend(recommenders)
        Assertions.assertThat(person1.willingToMeet).isTrue()
        Assertions.assertThat(person2.willingToMeet).isTrue()
    }
    @Test
    fun getNetwork() {
        val networkMember = Person()
        networkMember.willingToRecommend = true
        val stranger = Person()
        networkMember.willingToRecommend = false
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
}
