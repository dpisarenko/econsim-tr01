package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.DefaultSimulation

/**
 * Created by pisarenko on 04.05.2016.
 */
// TODO: Delete this
data class ScenarioResultsTuple(
        val sim:DefaultSimulation,
        val plPathActual:String,
        val plPathExpected:String,
        val csvPathActual:String,
        val csvPathExpected:String
)
