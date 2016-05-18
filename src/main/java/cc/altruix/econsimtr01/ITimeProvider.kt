/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01

import org.joda.time.DateTime

/**
 * Created by pisarenko on 18.05.2016.
 */
interface ITimeProvider {
    fun now():DateTime
}