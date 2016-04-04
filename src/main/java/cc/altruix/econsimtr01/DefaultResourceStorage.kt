package cc.altruix.econsimtr01

import com.google.common.util.concurrent.AtomicDouble
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class DefaultResourceStorage : IResourceStorage {
    val amountsByResource = HashMap<Resource, AtomicDouble>()

    override fun put(res: Resource, amt:Double) {
        var storedAmount = amountsByResource.get(res)
        if (storedAmount == null) {
            storedAmount = AtomicDouble(0.0)
            amountsByResource.put(res, storedAmount)
        }
        storedAmount.getAndAdd(amt)
    }
    override fun amount(res: Resource): Double {
        val mapAmt = amountsByResource.get(res)
        if (mapAmt != null) {
            return mapAmt.get()
        }
        return 0.0
    }

    override fun remove(res: Resource, amt: Double) {
        val mapAmt = amountsByResource.get(res)
        if (mapAmt != null) {
            mapAmt.getAndAdd(-amt)
        }
    }
}
