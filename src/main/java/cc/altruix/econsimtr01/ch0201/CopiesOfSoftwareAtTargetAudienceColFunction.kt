package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.getResults

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class CopiesOfSoftwareAtTargetAudienceColFunction : ITimeSeriesFieldFillerFunction {
    // TODO: Test this
    // TODO: Implement this

    override fun invoke(prolog: Prolog, time: Long): String =
            prolog.getResults(
                    "resourceLevel($time, stacy, r2, Amount).",
                    "Amount"
            ).first()
}
