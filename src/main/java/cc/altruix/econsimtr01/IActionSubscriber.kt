/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 15.04.2016.
 */
interface IActionSubscriber {
    fun actionOccurred(sender: IAction, time: DateTime)
}