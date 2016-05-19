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

/**
 * Created by pisarenko on 17.05.2016.
 */
enum class AgriculturalSimulationRowField(override val description:String,
                                          override val unit:String)
: RowField {
    SEEDS_IN_SHACK("Seeds in shack", "kg"),
    FIELD_AREA_WITH_SEEDS("Area with seeds", "Square Meters"),
    EMPTY_FIELD_AREA("Empty field area", "Square Meters"),
    FIELD_AREA_WITH_CROP("Field area with crop", "Square Meters")

}