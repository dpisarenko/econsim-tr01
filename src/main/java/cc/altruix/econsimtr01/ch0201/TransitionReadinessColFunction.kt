package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog

/**
 * Created by pisarenko on 20.04.2016.
 */
class TransitionReadinessColFunction : ITimeSeriesFieldFillerFunction {
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

    private fun softwareComplete(prolog: Prolog, time: Long): Boolean {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun enoughPeopleInList(prolog: Prolog, time: Long): Boolean {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    internal  fun enoughMoney(prolog: Prolog, time: Long): Boolean {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}