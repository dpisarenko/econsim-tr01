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

package cc.altruix.econsimtr01

import cc.altruix.econsimtr01.ch0202.SimResRow
import cc.altruix.econsimtr01.ch03.*
import org.joda.time.DateTime
import java.io.PrintStream
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
abstract class AbstractSimulationApp<T>(
        val cmdLineParamValidator: ICmdLineParametersValidator =
        CmdLineParametersValidator(),
        val timeProvider:ITimeProvider = TimeProvider(),
        val targetDir:String = System.getProperty("user.dir"),
        val csvFilePrefix:String) {

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
            SimResRow<T>>()
        val scenarioResults = scenarios
            .map {
                createSimulation(it, simResults)
            }
        scenarioResults.forEach {
            it.run()
        }
        val targetFileName = composeTargetFileName(csvFilePrefix)
        val simNames = scenarios.map { it.data["SimulationName"].toString() }
            .toList()
        val timeSeriesCreator = createTimeSeriesCreator(
            simResults,
            targetFileName,
            simNames)
        timeSeriesCreator.run()
    }

    abstract fun createSimulation(
        it: PropertiesFileSimParametersProvider,
        simResults: HashMap<
            DateTime,
            SimResRow<T>>
    ): ISimulation

    abstract fun createTimeSeriesCreator(
        simData: Map<DateTime, SimResRow<T>>,
        targetFileName: String,
        simNames: List<String>):TimeSeriesCreator<T>

    internal open fun composeTargetFileName(csvFilePrefix: String): String =
        "$targetDir/$csvFilePrefix-${timeProvider.now().millis}.csv"
    internal abstract fun
        createSemanticValidators():List<ISemanticSimulationParametersValidator>
}
