package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime

class Sim1(val logTarget:StringBuilder,
           val flows:MutableList<ResourceFlow>,
           simParametersProvider: Sim1ParametersProvider) : DefaultSimulation(simParametersProvider) {
    companion object {
        val RESOURCE_AVAILABLE_TIME = PlResource(
                id = "r1",
                name = "Available time",
                unit = "Hours"
        )

        /**
         * Parameters of process 1 (introduction) (START)
         */
        /**
         * Initial network size: Number of people, willing to recommend my friend at
         * the start of simulation (note that this number will change during the simulation).
         */
        val INITIAL_NETWORK_SIZE:Int = 100

        /**
         * Average activity his network - how often, on average, does a person inside
         * his network decides to recommend my friend to other people (percentage of
         * people inside the network, who recommend my friend to the people they know in
         * a given week).
         */
        val AVERAGE_NETWORK_ACTIVITY:Double = 0.1

        /**
         * Average suggestibility of strangers
         *
         * Average suggestibility of strangers - how likely is a stranger to change
         * his mind (become willing to meet my friend) after the recommendation of a
         * person inside the network (percentage of people outside the network, who were contacted by people
         * inside the network and actually agreed to meet my friend) ?
         */
        val AVERAGE_SUGGESTIBILITY_OF_STRANGERS:Double = 0.6
        /**
         * Parameters of process 1 (introduction) (END)
         */

        /**
         * Parameters of process 2 (offline networking session) (START)
         */
        /**
         * AVAILABLE_TIME_PER_WEEK
         * Unit: Hours per week
         *
         * Amount of time the protagonist can work
         */
        val TOTAL_TIME_FOR_OFFLINE_NETWORKING_PER_WEEK:Int = 40

        /**
         * TIME_PER_OFFLINE_NETWORKING_SESSION
         * Unit: Hours
         */
        val TIME_PER_OFFLINE_NETWORKING_SESSION:Double = 3.0

        /**
         * Maximum number of offline networking sessions, which the protagonist can
         * hold on one business day.
         * Unit: Times per (business) day
         */
        val MAX_NETWORKING_SESSIONS_PER_BUSINESS_DAY:Int = 3
        /**
         * Parameters of process 2 (offline networking session) (END)
         */
    }

    override fun continueCondition(time: DateTime): Boolean = time.year <= 1

    override fun createAgents(): List<IAgent> {
        val population = Population(INITIAL_NETWORK_SIZE)
        return listOf(
                Protagonist(TOTAL_TIME_FOR_OFFLINE_NETWORKING_PER_WEEK,
                        MAX_NETWORKING_SESSIONS_PER_BUSINESS_DAY,
                        TIME_PER_OFFLINE_NETWORKING_SESSION,
                        population
                )
        )
    }

    override fun createSensors(): List<ISensor> {
        // TODO: Implement this
        // TODO: Test this
        // TBD
        return emptyList()
    }
}
