package cc.altruix.econsimtr01

import cc.altruix.javaprologinterop.PlUtils
import org.apache.commons.io.IOUtils
import java.io.InputStream

/**
 * Created by pisarenko on 05.04.2016.
 */
class SimParametersProvider(val theoryTxt: String) {
    var maxDaysWithoutFood:Int = 0
        get
        private set
    var initialAmountOfPotatoes:Double = 0.0
        get
        private set
    var dailyPotatoConsumption:Double = 0.0
        get
        private set

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