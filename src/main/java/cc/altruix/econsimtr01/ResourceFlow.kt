package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 05.04.2016.
 */
data class ResourceFlow (
        val time: DateTime,
        val src:ISometingIdentifiable,
        val target:ISometingIdentifiable,
        val res:Resource,
        val amt:Double)