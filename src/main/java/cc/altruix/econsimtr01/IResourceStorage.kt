package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
interface IResourceStorage {
    fun put(res: Resource, amt:Double)
    fun amount(res: Resource):Double

    fun remove(res: Resource, amt: Double)
}
