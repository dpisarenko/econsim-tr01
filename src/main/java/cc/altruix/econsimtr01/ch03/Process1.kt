/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.IAction
import cc.altruix.econsimtr01.IActionSubscriber
import cc.altruix.econsimtr01.parseDayMonthString
import org.joda.time.DateTime

/**
 * Created by pisarenko on 16.05.2016.
 */
class Process1(val simParamProv:AgriculturalSimParametersProvider) : IAction {
    val start = simParamProv.data["Process1Start"].toString()
            .parseDayMonthString()
    val end  = simParamProv.data["Process1End"].toString()
            .parseDayMonthString()

    override fun timeToRun(time: DateTime): Boolean {
        val afterStart = (time.monthOfYear >= start.month) && (time
                .dayOfMonth >= start.day)
        val beforeEnd = (time.monthOfYear <= end.month) && (time.dayOfMonth
                <= end.day)

        // TODO: Implement this
        // TODO: Test this
        return afterStart && beforeEnd && (time.hourOfDay == 0) && (time
                .minuteOfHour == 0)
    }

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