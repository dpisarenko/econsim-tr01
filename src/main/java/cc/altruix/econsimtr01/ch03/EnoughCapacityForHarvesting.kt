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

import cc.altruix.econsimtr01.calculateBusinessDays
import cc.altruix.econsimtr01.createCorrectValidationResult
import cc.altruix.econsimtr01.createIncorrectValidationResult
import cc.altruix.econsimtr01.parseDayMonthString

/**
 * Created by pisarenko on 15.05.2016.
 */
open class EnoughCapacityForHarvesting :
        ISemanticSimulationParametersValidator {
    open override fun validate(scenario: PropertiesFileSimParametersProvider)
            : ValidationResult {
        val requiredEffort = calculateRequiredEffort(scenario)
        val availableTime = calculateAvailableTime(scenario)
        if (requiredEffort > availableTime) {
            return createIncorrectValidationResult(
                    "Process 3: It requires $requiredEffort hours of " +
                            "effort, but only $availableTime is available")
        }
        return createCorrectValidationResult()
    }

    open internal fun calculateAvailableTime(
            scenario: PropertiesFileSimParametersProvider): Double {
        val start = scenario.data["Process2End"].toString().parseDayMonthString()
        val end = scenario.data["Process3End"].toString().parseDayMonthString()
        val businessDays = calculateBusinessDays(start, end)
        val numberOfWorkers =
                scenario.data["NumberOfWorkers"].toString().toDouble()
        val workingTimePerBusinessDay =
                scenario.data["LaborPerBusinessDay"].toString().toDouble()
        return businessDays * numberOfWorkers * workingTimePerBusinessDay
    }

    open internal fun calculateRequiredEffort(scenario:
                                  PropertiesFileSimParametersProvider): Double
    {
        val fieldSize = scenario.data["SizeOfField"].toString().toDouble()
        val effortPerSquareMeter = scenario
                .data["Process3EffortPerSquareMeter"].toString().toDouble()
        return fieldSize * effortPerSquareMeter
    }
}