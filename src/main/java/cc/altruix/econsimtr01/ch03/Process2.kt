/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.IAction
import cc.altruix.econsimtr01.IActionSubscriber
import org.joda.time.DateTime

/**
 * Created by pisarenko on 16.05.2016.
 */
class Process2 : IAction {
    override fun timeToRun(time: DateTime): Boolean {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }

    override fun run(time: DateTime) {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }

    override fun notifySubscribers(time: DateTime) {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }

    override fun subscribe(subscriber: IActionSubscriber) {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }
}