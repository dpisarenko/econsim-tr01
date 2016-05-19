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
    val field = simParamProv.agents.find { it.id() == Field.ID }
            as DefaultAgent
    val shack = simParamProv.agents.find { it.id() == Shack.ID }
            as DefaultAgent
    override fun timeToRun(time: DateTime): Boolean =
            businessDay(time) &&
            timeBetweenStartAndEnd(time) &&
            evenHourAndMinute(time) &&
            fieldNotFull(field)

    open internal fun businessDay(time: DateTime): Boolean =
            time.isBusinessDay()

    open internal fun evenHourAndMinute(time:DateTime):Boolean =
            time.evenHourAndMinute(8, 0)

    open internal fun timeBetweenStartAndEnd(time: DateTime) =
            time.between(start, end)

    open internal fun fieldNotFull(field: DefaultAgent): Boolean =
            fieldAreaWithSeeds(field) < sizeOfField()

    internal fun sizeOfField() = simParamProv.data["SizeOfField"].toString()
            .toDouble()

    internal fun fieldAreaWithSeeds(field: DefaultAgent): Double {
        return field.amount(
                AgriculturalSimParametersProvider
                        .RESOURCE_AREA_WITH_SEEDS.id
        )
    }

    override fun run(time: DateTime) {
        val workersCount = simParamProv.data["NumberOfWorkers"].toString()
                .toDouble()
        val laborPerDay = simParamProv.data["LaborPerBusinessDay"].toString()
                .toDouble()
        val effortPerSquareMeter =
                simParamProv.data["Process1EffortInSquareMeters"].toString()
                .toDouble()
        val maxDailyProcessedArea = (workersCount * laborPerDay) /
                effortPerSquareMeter
        val dailyProcessedArea =
                Math.min(maxDailyProcessedArea,
                        field.amount(
                                AgriculturalSimParametersProvider.
                                        RESOURCE_EMPTY_AREA.id
                        )
                )
        if (dailyProcessedArea == 0.0) {
            return
        }
        val seedsqPerSquareMeter = simParamProv
                .data["Process1QuantityOfSeeds"].toString().toDouble()
        val dailySeeds = Math.min(dailyProcessedArea * seedsqPerSquareMeter,
                shack.amount(
                        AgriculturalSimParametersProvider.RESOURCE_SEEDS.id
                )
        )

        field.remove(
                AgriculturalSimParametersProvider.RESOURCE_EMPTY_AREA.id,
                dailyProcessedArea
        )
        field.put(
                AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_SEEDS.id,
                dailyProcessedArea
        )
        shack.remove(
                AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
                dailySeeds
        )
    }
    override fun notifySubscribers(time: DateTime) {
    }
    override fun subscribe(subscriber: IActionSubscriber) {
    }
}
