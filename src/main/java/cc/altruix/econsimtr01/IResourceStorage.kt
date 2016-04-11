package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
interface IResourceStorage : ISometingIdentifiable {

    fun put(res: String, amt:Double)
    fun amount(res: String):Double

    fun remove(res: String, amt: Double)
}
