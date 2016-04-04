package cc.altruix.econsimtr01

import com.google.common.util.concurrent.AtomicDouble
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class DefaultResourceStorage : IResourceStorage {
    val amountsByResource = HashMap<IResource, AtomicDouble>()
    override fun put(res: IResource, amt:Double) {
        var storedAmount = amountsByResource.get(res)
        if (storedAmount == null) {
            storedAmount = AtomicDouble(0.0)
            amountsByResource.put(res, storedAmount)
        }
        storedAmount.getAndAdd(amt)
    }

    override fun amount(res: IResource): Double {
        val mapAmt = amountsByResource.get(res)
        if (mapAmt != null) {
            return mapAmt.get()
        }
        return 0.0
    }
}
