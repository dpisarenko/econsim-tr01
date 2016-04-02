package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
data class SimResult(val time:PointInTime, val state:List<ISimStateDescriptor>)
