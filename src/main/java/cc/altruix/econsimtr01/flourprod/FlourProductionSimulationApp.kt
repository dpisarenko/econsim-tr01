/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
