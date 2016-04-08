package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.toPrologTheory

/**
 * Created by pisarenko on 08.04.2016.
 */
class Sim1ParametersProvider(val theoryTxt: String) {
    init {
        val prolog = theoryTxt.toPrologTheory()
    }
}