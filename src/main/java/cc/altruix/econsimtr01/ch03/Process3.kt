/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
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
    var shack = simParamProv.agents.find { it.id() == Shack.ID }
            as DefaultAgent
    override fun timeToRun(time: DateTime): Boolean =
        businessDay(time) &&
        timeBetweenStartAndEnd(time) &&
        evenHourAndMinute(time) &&
        cropToCollectAvailable()

    open internal fun businessDay(time: DateTime): Boolean =
            time.isBusinessDay()

    open internal fun evenHourAndMinute(time: DateTime) =
            time.evenHourAndMinute(8, 0)

    open internal fun timeBetweenStartAndEnd(time: DateTime) =
            time.between(start, end)

    open internal fun cropToCollectAvailable(): Boolean =
            field.amount(
                    AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_CROP.id
            ) > 0.0

    override fun run(time: DateTime) {
        val workerCount = simParamProv.data["NumberOfWorkers"]
                .toString().toDouble()
        val workingTimePerDay =
                simParamProv.data["LaborPerBusinessDay"].toString().toDouble()
        val effortPerSquareMeter =
                simParamProv.data["Process3EffortPerSquareMeter"]
                        .toString().toDouble()
        val areaWorkersCanProcess =
                (workerCount * workingTimePerDay) / effortPerSquareMeter
        val totalAreaToProcess = field.amount(
                AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_CROP.id
        )
        val processedArea = Math.min(areaWorkersCanProcess, totalAreaToProcess)

        val yieldPerSquareMeter = simParamProv
                .data["Process2YieldPerSquareMeter"].toString().toDouble()
        val cropAmount = yieldPerSquareMeter * processedArea

        field.remove(
                AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_CROP.id,
                processedArea
        )
        field.put(
                AgriculturalSimParametersProvider.RESOURCE_EMPTY_AREA.id,
                processedArea
        )
        field.remove(
                AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
                cropAmount
        )
        shack.put(
                AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
                cropAmount
        )
    }

    override fun notifySubscribers(time: DateTime) {
    }

    override fun subscribe(subscriber: IActionSubscriber) {
    }
}