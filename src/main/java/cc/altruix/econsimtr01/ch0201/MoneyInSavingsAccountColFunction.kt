package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.getResults

/**
 * Created by pisarenko on 20.04.2016.
 */
class MoneyInSavingsAccountColFunction : ITimeSeriesFieldFillerFunction {
    override fun invoke(prolog: Prolog, time: Long): String =
            prolog.getResults("resourceLevel($time, savingsAccount, r2, Amount).", "Amount").first()
}