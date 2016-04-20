package cc.altruix.econsimtr01.ch0201

import org.joda.time.DateTime

/**
 * Created by pisarenko on 20.04.2016.
 */
class WhenResourceReachesLevel(val agent:String,
                               val resource:String,
                               val amount:Double) : (DateTime) -> Boolean {
    override fun invoke(time: DateTime): Boolean {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }
}