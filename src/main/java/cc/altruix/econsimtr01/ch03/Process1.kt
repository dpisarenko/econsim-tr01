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
open class Process1(val simParamProv:PropertiesFileSimParametersProvider) :
    IAction {
    val LOGGER = LoggerFactory.getLogger(Process1::class.java)
    val start = simParamProv.data["Process1Start"].toString()
            .parseDayMonthString()
    val end  = simParamProv.data["Process1End"].toString()
            .parseDayMonthString()
    // TODO: Test that Process1 actually finds the field
    val field = simParamProv.agents.find { it.id() == Field.ID }
            as DefaultAgent

    override fun timeToRun(time: DateTime): Boolean =
            timeBetweenStartAndEnd(time) &&
            evenHourAndMinute(time) &&
            fieldNotFull(field)

    open internal fun evenHourAndMinute(time:DateTime):Boolean =
            (time.hourOfDay == 8) && (time.minuteOfHour == 0)

    open internal fun timeBetweenStartAndEnd(time: DateTime) =
            time.between(start, end)

    // TODO: Implement this
    open internal fun fieldNotFull(field: DefaultAgent): Boolean =
            field.amount(
                    AgriculturalSimParametersProvider
                            .RESOURCE_AREA_WITH_SEEDS.id
            ) < simParamProv.data["SizeOfField"].toString().toDouble()

    override fun run(time: DateTime) {
        // TODO: Implement this
        // TODO: Test this

    }

    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }
}
