/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

/**
 * Created by pisarenko on 17.05.2016.
 */
enum class AgriculturalSimulationRowField(val description:String,
                                          val unit:String) {
    SEEDS_IN_SHACK("Seeds in shack", "kg"),
    FIELD_AREA_WITH_SEEDS("Area with seeds", "Square Meters"),
    EMPTY_FIELD_AREA("Empty field area", "Square Meters"),
    FIELD_AREA_WITH_CROP("Field area with crop", "Square Meters")

}