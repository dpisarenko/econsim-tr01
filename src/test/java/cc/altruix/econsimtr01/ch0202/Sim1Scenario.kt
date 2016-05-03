package cc.altruix.econsimtr01.ch0202

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
data class Sim1Scenario(
        val initialPopulation:Int,
        val averageNetworkActivity:Double,
        var averageSuggestibilityOfStrangers:Double
)
