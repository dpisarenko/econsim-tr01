package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.ch0201.OncePerWeek
import cc.altruix.econsimtr01.millisToSimulationDateTime
import cc.altruix.econsimtr01.mock
import cc.altruix.econsimtr01.newLine
import cc.altruix.econsimtr01.toSimulationDateTimeString
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito
import java.io.File
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

    @Test
    fun simulationWithDifferentParameters() {
        val scenarioDescriptors = listOf(
                Sim1ScenarioDescriptor(100, 0.1, 0.6),
                Sim1ScenarioDescriptor(100, 0.05, 0.1),
                Sim1ScenarioDescriptor(1000, 0.05, 0.1),
                Sim1ScenarioDescriptor(1000, 0.1, 0.6)
        )
        val simDescriptorsAndObjects = scenarioDescriptors.map {
            createSimObjects(it)
        }.toList()
        val data = HashMap<DateTime,Map<Sim1ScenarioDescriptor,Double>>()
        var t = 0L.millisToSimulationDateTime()
        for (day in 1..(365*2)) {
            val rowData = HashMap<Sim1ScenarioDescriptor,Double>()
            data.put(t, rowData)
            simDescriptorsAndObjects.forEach {
                val descriptor = it.first
                val objects = it.second
                objects.process.run(t)
                val peopleWillingToMeet = calculatePeopleWillingToMeet(objects.process.population)
                rowData.put(descriptor, peopleWillingToMeet)
            }
            t = t.plusDays(1)
        }
        val builder = StringBuilder()
        val times = data.keys.sorted()
        builder.append("Time;")
        scenarioDescriptors.forEach { sim ->
            builder.append("\"")
            builder.append(sim.toString())
            builder.append("\"")
            builder.append(";")
        }
        builder.newLine()
        times.forEach { t ->
            val rowData = data.get(t)
            builder.append(t.toSimulationDateTimeString())
            builder.append(";")
            scenarioDescriptors.forEach { sim ->
                builder.append(rowData?.get(sim))
                builder.append(";")
            }
            builder.newLine()
        }
        val expectedSimResultsFileName =
                "src/test/resources/ch0202/sim01/IntroductionProcessTests.simulationWithDifferentParameters.expected.csv"
        val expectedResult = File(expectedSimResultsFileName).readText()
        Assertions.assertThat(builder.toString()).isEqualTo(expectedResult)
    }

    private fun calculatePeopleWillingToMeet(population: IPopulation): Double =
            population.people().filter { it.willingToMeet }.count().toDouble()

    private fun createSimObjects(it: Sim1ScenarioDescriptor): Pair<Sim1ScenarioDescriptor, Sim1ScenarioObjects> {
        val population = Population(it.initialNetworkSize)

        val process = IntroductionProcess(
                population = population,
                triggerFun = OncePerWeek("Monday"),
                averageNetworkActivity =
                it.averageNetworkActivity,
                averageSuggestibilityOfStrangers =
                it.averageSuggestibilityOfStrangers
        )
        return Pair(it, Sim1ScenarioObjects(Population(it.initialNetworkSize), process))
    }

    private fun createPerson(willingToRecommend:Boolean):Person
    {
        val result = Person()
        result.willingToRecommend = willingToRecommend
        return result
    }
}
