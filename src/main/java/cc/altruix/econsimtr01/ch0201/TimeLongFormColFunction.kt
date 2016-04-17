package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.removeSingleQuotes
import cc.altruix.javaprologinterop.PlUtils

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class TimeLongFormColFunction : ITimeSeriesFieldFillerFunction {
    // TODO: Test this
    override fun invoke(prolog: Prolog, t: Long): String =
            PlUtils.extractSingleStringFromQuery(
                    prolog,
                    "measurementTime($t, X).", "X"
            ).removeSingleQuotes()
}
