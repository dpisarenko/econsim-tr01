package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
interface IResourceStorage {
    fun put(res:IResource, amt:Double)
    fun amount(res:IResource):Double
}
