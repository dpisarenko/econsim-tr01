/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.*
import cc.altruix.econsimtr01.ch0201.InfiniteResourceSupply
import cc.altruix.econsimtr01.ch0201.InitialResourceLevel

/**
 * Created by pisarenko on 26.04.2016.
 */
class Sim1ParametersProvider(override val agents: MutableList<IAgent>,
                             override val flows: MutableList<PlFlow>,
                             override val initialResourceLevels: MutableList<InitialResourceLevel>,
                             override val infiniteResourceSupplies: MutableList<InfiniteResourceSupply>,
                             override val transformations: MutableList<PlTransformation>,
                             val initialNetworkSize:Int = INITIAL_NETWORK_SIZE,
                             val averageNetworkActivity:Double = AVERAGE_NETWORK_ACTIVITY,
                             val averageSuggestibilityOfStrangers:Double = AVERAGE_SUGGESTIBILITY_OF_STRANGERS,
                             val totalTimeForOfflineNetworkingPerWeek:Int = TOTAL_TIME_FOR_OFFLINE_NETWORKING_PER_WEEK,
                             val timePerOfflineNetworkingSession:Double = TIME_PER_OFFLINE_NETWORKING_SESSION,
                             val maxNetworkingSessionsPerBusinessDay:Int = MAX_NETWORKING_SESSIONS_PER_BUSINESS_DAY,
                             val recommendationConversion:Double = RECOMMENDATION_CONVERSION,
                             val willingnessToPurchaseConversion:Double = WILLINGNESS_TO_PURCHASE_CONVERSION,
                             val name:String = "")
: ISimParametersProvider {
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
         * Recommendation conversion: Percentage of people, who become willing to recommend my friend after they
         * had lunch with him.
         */
        val RECOMMENDATION_CONVERSION:Double = 0.5

        /**
         * Willingness to purchase conversion: Percentage of people, who decide that they will use my friend's service,
         * should they ever need to buy or sell a property.
         */
        val WILLINGNESS_TO_PURCHASE_CONVERSION:Double = 0.5

        /**
         * Parameters of process 2 (offline networking session) (END)
         */
    }

}