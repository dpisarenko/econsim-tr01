/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime

/**
 * Created by pisarenko on 16.05.2016.
 */
open class Process3(val simParamProv:AgriculturalSimParametersProvider) :
IAction {
    val start = simParamProv.data["Process2End"].toString()
            .parseDayMonthString()
    val end  = simParamProv.data["Process3End"].toString()
            .parseDayMonthString()
    var field = simParamProv.agents.find { it.id() == Field.ID }
            as DefaultAgent
    // TODO: Implement this
    // TODO: Test this
    override fun timeToRun(time: DateTime): Boolean =
        time.between(start, end) &&
        time.evenHourAndMinute(8, 0) &&
        cropToCollectAvailable()

    // TODO: Test this
    open internal fun cropToCollectAvailable(): Boolean =
            field.amount(
                    AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_CROP.id
            ) > 0.0

    override fun run(time: DateTime) {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }

    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }
}