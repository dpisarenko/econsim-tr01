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

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.ITimeProvider
import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.TimeProvider
import cc.altruix.econsimtr01.ch0202.SimResRow
import org.joda.time.DateTime
import java.io.PrintStream
import java.util.*

/**
 * Created by pisarenko on 13.05.2016.
 */
class BasicAgriculturalSimulationApp(
        val cmdLineParamValidator:ICmdLineParametersValidator =
            CmdLineParametersValidator(),
        val timeProvider:ITimeProvider = TimeProvider(),
        val targetDir:String = System.getProperty("user.dir")
) {
    fun run(args: Array<String>,
            out: PrintStream,
            err: PrintStream) {
        val cmdLineParamValRes = cmdLineParamValidator.validate(args)
        if (!cmdLineParamValRes.valid) {
            err.println(cmdLineParamValRes.message)
            return
        }
        val validators = createSemanticValidators()
        val scenarios = cmdLineParamValidator.simParamProviders
        val valRes = LinkedList<ValidationResult>()
        scenarios.forEach { scenario ->
            validators.map { it.validate(scenario) }.forEach { valRes.add(it) }
        }
        val error = valRes.find { it.valid == false }
        if (error != null) {
            val allErrors = valRes.filter { it.valid == false }
                    .map { it.message }
                    .joinToString(separator = ", ")
            err.println("One or more scenarios are invalid:")
            err.println(allErrors)
            return
        }
        val simResults = HashMap<DateTime,
                SimResRow<AgriculturalSimulationRowField>>()
        val scenarioResults = scenarios
                .map { it as AgriculturalSimParametersProvider }
                .map {
                    BasicAgriculturalSimulation(
                        logTarget = StringBuilder(),
                        flows = ArrayList<ResourceFlow>(),
                        simParametersProvider = it,
                        resultsStorage = simResults
                    )
                }
        scenarioResults.forEach {
            it.run()
        }
        val targetFileName = composeTargetFileName()
        val simNames = scenarios.map { it.data["SimulationName"].toString() }
                .toList()
        val timeSeriesCreator = AgriculturalSimulationTimeSeriesCreator(
                simResults,
                targetFileName,
                simNames)
        timeSeriesCreator.run()
    }

    internal open fun composeTargetFileName(): String =
            "$targetDir/agriculture-${timeProvider.now().millis}.csv"

    fun createSemanticValidators():List<ISemanticSimulationParametersValidator>
            = listOf(
                    EnoughCapacityForPuttingSeedsIntoGround(),
                    EnoughCapacityForHarvesting(),
                    OneDateBeforeOtherValidator("Process1Start", "Process1End"),
                    OneDateBeforeOtherValidator("Process2End", "Process3End"),
                    EnoughSeedsAtTheStartValidator()
            )
}
fun main(args : Array<String>) {
    println("Basic agriculture simulation")
    println("(C) Copyright 2016 Dmitri Pisarenko")
    BasicAgriculturalSimulationApp().run(args, System.out, System.err)
}
