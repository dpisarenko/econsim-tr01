package cc.altruix.econsimtr01.flourprod

import cc.altruix.econsimtr01.*
import cc.altruix.econsimtr01.ch0202.SimResRow
import cc.altruix.econsimtr01.ch03.BasicAgriculturalSimulationApp
import cc.altruix.econsimtr01.ch03.ICmdLineParametersValidator
import cc.altruix.econsimtr01.ch03.ISemanticSimulationParametersValidator
import cc.altruix.econsimtr01.ch03.PropertiesFileSimParametersProvider
import org.joda.time.DateTime
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class FlourProductionSimulationApp(
    cmdLineParamValidator: ICmdLineParametersValidator =
        FlourProductionSimCmdLineParametersValidator(),
    timeProvider: ITimeProvider = TimeProvider(),
    targetDir:String = System.getProperty("user.dir"))
    : AbstractSimulationApp<FlourProductionSimRowField>(
    cmdLineParamValidator,
    timeProvider,
    targetDir,
    "flourprod")  {
    override fun createSimulation(
        it: PropertiesFileSimParametersProvider,
        simResults: HashMap<DateTime, SimResRow<FlourProductionSimRowField>>):
        ISimulation {

        FlourProductionSimulation(logTarget = StringBuilder(),
            flows = ArrayList<ResourceFlow>(),
            simParametersProvider =
            it as FlourProductionSimulationParametersProvider,
            resultsStorage = simResults
        )
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }

    override fun createTimeSeriesCreator(
        simData: Map<DateTime, SimResRow<FlourProductionSimRowField>>,
        targetFileName: String, simNames: List<String>):
        TimeSeriesCreator<FlourProductionSimRowField> {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }

    override fun createSemanticValidators():
        List<ISemanticSimulationParametersValidator> {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }
    // TODO: Implement this
}
fun main(args : Array<String>) {

    println("Flour production simulation")
    println("(C) Copyright 2016 Dmitri Pisarenko")
    BasicAgriculturalSimulationApp().run(args, System.out, System.err)
}
