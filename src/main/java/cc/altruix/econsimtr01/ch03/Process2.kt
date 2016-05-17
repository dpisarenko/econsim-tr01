/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * Created by pisarenko on 16.05.2016.
 */
class Process2(val simParamProv:AgriculturalSimParametersProvider) : IAction {
    val LOGGER = LoggerFactory.getLogger(Process2::class.java)
    val end  = simParamProv.data["Process2End"].toString()
            .parseDayMonthString()

    // TODO: Make sure this process converts RESOURCE_AREA_WITH_SEEDS to
    // RESOURCE_AREA_WITH_CROP

    // TODO: Test this
    override fun timeToRun(time: DateTime): Boolean =
            time.isEqual(end.toDateTime(time.year)) &&
            evenHourAndMinute(time)

    // TODO: Implement this
    // TODO: Test this
    open internal fun evenHourAndMinute(time: DateTime): Boolean = time
            .evenHourAndMinute(0, 0)

    // TODO: Implement this
    // TODO: Test this
    override fun run(time: DateTime) {
        throw UnsupportedOperationException()
    }

    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }
}