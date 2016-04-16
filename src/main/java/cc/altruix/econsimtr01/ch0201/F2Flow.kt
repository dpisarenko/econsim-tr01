package cc.altruix.econsimtr01.ch0201

import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
open class F2Flow(id:String,
             src: String,
             target:String,
             resource:String,
             amount:Double?,
             timeTriggerFunction: (DateTime) -> Boolean,
             val priceOfOneCopyOfSoftware:Double) :
        ListRelatedFlow(id, src, target, resource, amount, timeTriggerFunction) {
    override fun run(time: DateTime) {
        // TODO: Test this
        val priceOfSoftwareSoldToNewlyActivatedAudience =
                calculatePriceOfSoftwareSold()
        this.run(priceOfSoftwareSoldToNewlyActivatedAudience,
                time)
    }

    fun calculatePriceOfSoftwareSold(): Double {
        // TODO: Test this
        return 0.0
    }

}
