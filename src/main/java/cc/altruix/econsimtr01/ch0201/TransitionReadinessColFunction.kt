package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog

/**
 * Created by pisarenko on 20.04.2016.
 */
class TransitionReadinessColFunction : ITimeSeriesFieldFillerFunction {
    companion object {
        val moneyInSavingsAccountExtractor = MoneyInSavingsAccountColFunction()
        val peopleInListExtractor = SubscribersCohortColFunction(1)
        val softwareCompletionExtractor = PercentageResourceLevelColFunction("stacy", "r14", 480.0)
    }
    override fun invoke(prolog: Prolog, time: Long): String
            {
        // TODO: Test this
        if (enoughMoney(prolog, time) &&
                enoughPeopleInList(prolog, time) &&
                softwareComplete(prolog, time)) {
            return "yes"
        }
        return "no"
    }

    internal fun softwareComplete(prolog: Prolog, time: Long): Boolean =
            softwareCompletionExtractor.invoke(prolog, time).toDouble() >= 100.0

    internal fun enoughPeopleInList(prolog: Prolog, time: Long): Boolean =
            peopleInListExtractor.invoke(prolog, time).toDouble() >= 1000.0

    internal fun enoughMoney(prolog: Prolog, time: Long): Boolean =
            moneyInSavingsAccountExtractor.invoke(prolog, time).toDouble() >= 3000.0
}