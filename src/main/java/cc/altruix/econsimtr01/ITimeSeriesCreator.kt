package cc.altruix.econsimtr01

import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
interface ITimeSeriesCreator {
    fun prologToCsv(input: File): String
}
