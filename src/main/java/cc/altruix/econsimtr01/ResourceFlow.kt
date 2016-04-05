package cc.altruix.econsimtr01

/**
 * Created by pisarenko on 05.04.2016.
 */
data class ResourceFlow (
        val time:Long,
        val src:ISometingIdentifiable,
        val target:ISometingIdentifiable,
        val res:Resource,
        val amt:Double)