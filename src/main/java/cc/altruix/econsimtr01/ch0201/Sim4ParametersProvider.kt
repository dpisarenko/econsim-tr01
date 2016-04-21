package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import alice.tuprolog.SolveInfo
import cc.altruix.econsimtr01.PlFlow

/**
 * Created by pisarenko on 19.04.2016.
 */
open class Sim4ParametersProvider(theoryTxt2:String) :
        Sim2ParametersProvider(theoryTxt2) {
    override fun createFlow(res: SolveInfo, prolog: Prolog): PlFlow =
            super.createFlow(extractFlowData(res))

}