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
         * OFFLINE_NETWORKING_INTENSITY
         * Unit: People per week
         *
         * Number of people, which he didn't previously know, and
         * which he meets every week.
         */
        val OFFLINE_NETWORKING_INTENSITY:Int = 5

        /**
         * AVAILABLE_TIME_PER_WEEK
         * Unit: Hours per week
         *
         * Amount of time the protagonist can work
         */
        val AVAILABLE_TIME_PER_WEEK:Int = 40

        /**
         * Maximum number of offline networking sessions, which the protagonist can
         * hold on one business day.
         * Unit: Times per (business) day
         */
        val MAX_NETWORKING_SESSIONS_PER_BUSINESS_DAY:Int = 3

        val INITIAL_NETWORK_SIZE:Int = 100
    }

    override fun continueCondition(time: DateTime): Boolean = time.year <= 1

    override fun createAgents(): List<IAgent> {
        val population = Population(INITIAL_NETWORK_SIZE)
        return listOf(
                Protagonist(
                        OFFLINE_NETWORKING_INTENSITY,
                        AVAILABLE_TIME_PER_WEEK,
                        MAX_NETWORKING_SESSIONS_PER_BUSINESS_DAY,
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
