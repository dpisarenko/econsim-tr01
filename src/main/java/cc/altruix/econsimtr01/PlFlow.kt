package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 09.04.2016.
 */
data class PlFlow(val id:String,
                  val src: String,
                  val target:String,
                  val resource:String,
                  val amount:Double?,
                  val timeTriggerFunction: (DateTime) -> Boolean)
