package cc.altruix.econsimtr01.flourprod

import cc.altruix.econsimtr01.*
import cc.altruix.econsimtr01.ch0202.SimResRow
import cc.altruix.econsimtr01.ch03.*
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
        ISimulation =
        FlourProductionSimulation(logTarget = StringBuilder(),
            flows = ArrayList<ResourceFlow>(),
            simParametersProvider =
            it as FlourProductionSimulationParametersProvider,
            resultsStorage = simResults
        )

    override fun createTimeSeriesCreator(
        simData: Map<DateTime, SimResRow<FlourProductionSimRowField>>,
        targetFileName: String, simNames: List<String>):
        TimeSeriesCreator<FlourProductionSimRowField> =
        FlourProductionSimulationTimeSeriesCreator(
            simData = simData,
            targetFileName = targetFileName,
            simNames = simNames
        )

    // TODO: Implement createSemanticValidators (add flour production
    // simulation specific semantic validators)
    override fun createSemanticValidators():
        List<ISemanticSimulationParametersValidator> = listOf(
        EnoughCapacityForPuttingSeedsIntoGround(),
        EnoughCapacityForHarvesting(),
        OneDateBeforeOtherValidator("Process1Start", "Process1End"),
        OneDateBeforeOtherValidator("Process2End", "Process3End"),
        EnoughSeedsAtTheStartValidator()
    )
}
fun main(args : Array<String>) {
    println("Flour production simulation")
    println("(C) Copyright 2016 Dmitri Pisarenko")
    FlourProductionSimulationApp().run(args, System.out, System.err)
}