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
class Process1(val simParamProv:AgriculturalSimParametersProvider) : IAction {
    val LOGGER = LoggerFactory.getLogger(Process1::class.java)
    val start = simParamProv.data["Process1Start"].toString()
            .parseDayMonthString()
    val end  = simParamProv.data["Process1End"].toString()
            .parseDayMonthString()
    // TODO: Test that Process1 actually finds the field
    val field = simParamProv.agents.find { it.id() == Field.ID } as DefaultAgent

    override fun timeToRun(time: DateTime): Boolean {
        val afterStart = (time.monthOfYear >= start.month) && (time
                .dayOfMonth >= start.day)
        val beforeEnd = (time.monthOfYear <= end.month) && (time.dayOfMonth
                <= end.day)
        val evenHourAndMinute =
                (time.hourOfDay == 0) && (time.minuteOfHour == 0)
        if (field == null) {
            LOGGER.error("Can't find field")
            return false
        }
        // TODO: Test this
        return afterStart && beforeEnd && evenHourAndMinute &&
                fieldNotFull(field)
    }
    // TODO: Implement this
    private fun fieldNotFull(field: DefaultAgent): Boolean =
            field.amount(
                    AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_SEEDS.id
            ) < simParamProv.data["SizeOfField"].toString().toDouble()

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