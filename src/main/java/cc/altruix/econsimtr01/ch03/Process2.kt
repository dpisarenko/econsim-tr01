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
class Process2(val simParamProv:AgriculturalSimParametersProvider) : IAction {
    val LOGGER = LoggerFactory.getLogger(Process2::class.java)
    val end  = simParamProv.data["Process2End"].toString()
            .parseDayMonthString()
    var field = simParamProv.agents.find { it.id() == Field.ID }
            as DefaultAgent
    override fun timeToRun(time: DateTime): Boolean =
            time.isEqual(end.toDateTime(time.year)) &&
            evenHourAndMinute(time)
    open internal fun evenHourAndMinute(time: DateTime): Boolean = time
            .evenHourAndMinute(0, 0)
    override fun run(time: DateTime) {
        val areaWithSeeds = field.amount(AgriculturalSimParametersProvider
                .RESOURCE_AREA_WITH_SEEDS.id)
        if (areaWithSeeds == 0.0) {
            LOGGER.error("There are no seeds on the field")
            return
        }
        val yieldPerSquareMeter = simParamProv
                .data["Process2YieldPerSquareMeter"].toString().toDouble()

        field.remove(
                AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_SEEDS.id,
                areaWithSeeds
        )
        field.put(
                AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_CROP.id,
                areaWithSeeds
        )
        field.put(
                AgriculturalSimParametersProvider.RESOURCE_SEEDS.id,
                areaWithSeeds * yieldPerSquareMeter
        )
    }
    override fun notifySubscribers(time: DateTime) {
    }
    override fun subscribe(subscriber: IActionSubscriber) {
    }
}