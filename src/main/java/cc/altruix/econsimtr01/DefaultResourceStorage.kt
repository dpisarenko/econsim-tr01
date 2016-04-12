package cc.altruix.econsimtr01

import com.google.common.util.concurrent.AtomicDouble
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class DefaultResourceStorage(val id:String) : IResourceStorage {
    val amountsByResource = HashMap<String, AtomicDouble>()
    val infiniteResources = HashSet<String>()

    override fun put(res: String, amt:Double) {
        var storedAmount = amountsByResource.get(res)
        if (storedAmount == null) {
            storedAmount = AtomicDouble(0.0)
            amountsByResource.put(res, storedAmount)
        }
        storedAmount.getAndAdd(amt)
    }

    override fun amount(res: String): Double {
        val mapAmt = amountsByResource.get(res)
        if (mapAmt != null) {
            return mapAmt.get()
        }
        return 0.0
    }

    override fun remove(res: String, amt: Double) {
        val mapAmt = amountsByResource.get(res)
        if (mapAmt != null) {
            mapAmt.getAndAdd(-amt)
        }
    }

    override fun setInfinite(res: String) {
        infiniteResources.add(res)
    }

    override fun isInfinite(res:String):Boolean = infiniteResources.contains(res)

    override fun id(): String = id
}
