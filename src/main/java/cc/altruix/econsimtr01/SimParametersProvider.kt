package cc.altruix.econsimtr01

import cc.altruix.javaprologinterop.PlUtils
import org.apache.commons.io.IOUtils
import java.io.InputStream

/**
 * Created by pisarenko on 05.04.2016.
 */
class SimParametersProvider(val stream: InputStream) {
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
        val prolog = PlUtils.createEngine()
        val theoryTxt = IOUtils.toString(stream)
        PlUtils.loadPrologTheoryAsText(prolog, theoryTxt)

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