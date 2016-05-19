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

package cc.altruix.econsimtr01

import cc.altruix.econsimtr01.ch0201.InfiniteResourceSupply
import cc.altruix.econsimtr01.ch0201.InitialResourceLevel
import cc.altruix.javaprologinterop.PlUtils
import java.util.*

/**
 * Created by pisarenko on 05.04.2016.
 */
class SimParametersProvider(val theoryTxt: String,
                            override val agents: MutableList<IAgent>,
                            override val flows: MutableList<PlFlow>,
                            override val initialResourceLevels: MutableList<InitialResourceLevel>,
                            override val infiniteResourceSupplies: MutableList<InfiniteResourceSupply>) : ISimParametersProvider {
    var maxDaysWithoutFood:Int = 0
        get
        private set
    var initialAmountOfPotatoes:Double = 0.0
        get
        private set
    var dailyPotatoConsumption:Double = 0.0
        get
        private set
    override val transformations:MutableList<PlTransformation> = LinkedList()
        get

    init {
        val prolog = theoryTxt.toPrologTheory()
        this.maxDaysWithoutFood = PlUtils.extractSingleInt(
                prolog,
                "maxDaysWithoutFood(X).",
                "X"
        )
        this.initialAmountOfPotatoes = PlUtils.extractSingleStringFromQuery(
                prolog,
                "initialAmountOfPotatoes(X).",
                "X"
        ).toDouble()
        this.dailyPotatoConsumption = PlUtils.extractSingleStringFromQuery(
                prolog,
                "dailyPotatoConsumption(X).",
                "X"
        ).toDouble()
    }
}