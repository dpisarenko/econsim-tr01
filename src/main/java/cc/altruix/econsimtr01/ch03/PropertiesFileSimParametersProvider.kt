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

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ISimParametersProvider
import cc.altruix.econsimtr01.PlFlow
import cc.altruix.econsimtr01.PlTransformation
import cc.altruix.econsimtr01.ch0201.InfiniteResourceSupply
import cc.altruix.econsimtr01.ch0201.InitialResourceLevel
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 14.05.2016.
 */
abstract open class PropertiesFileSimParametersProvider(val file: File) :
        ISimParametersProvider {
    override val agents:MutableList<IAgent> = LinkedList()
        get
    override val flows:MutableList<PlFlow> = LinkedList()
        get

    override val initialResourceLevels:MutableList<InitialResourceLevel> =
            LinkedList()
        get
    override val infiniteResourceSupplies:MutableList<InfiniteResourceSupply> =
            LinkedList()
        get
    override val transformations:MutableList<PlTransformation> = LinkedList()
        get
    lateinit var validity:ValidationResult
        get
    lateinit var data:Properties

    open fun initAndValidate() {
        val validators = createValidators()
        data = loadData()
        val valResults = createValResults()
        validators.entries.forEach { entry ->
            applyValidators(data, valResults, entry.key, entry.value)
        }
        val valid = calculateValidity(valResults)
        var message = createMessage(valResults, valid)
        validity = ValidationResult(valid, message)
    }

    open internal fun createMessage(valResults: List<ValidationResult>,
                                    valid: Boolean): String {
        var message = ""
        if (!valid) {
            message = valResults.filter { it.valid == false }
                    .map { it.message }.joinToString(separator = ", ")
        }
        return message
    }

    open internal fun calculateValidity(valResults: List<ValidationResult>) =
            valResults.filter { it.valid == false }.count() < 1

    open internal fun applyValidators(data: Properties,
                                      valResults: MutableList<ValidationResult>,
                                      parameter: String,
                                      parameterValidators:
                                      List<IPropertiesFileValueValidator>) {
        for (validator in parameterValidators) {
            val vres = validator.validate(data, parameter)
            if (!vres.valid) {
                valResults.add(vres)
                break;
            }
        }
    }

    open internal fun createValResults():MutableList<ValidationResult> =
            LinkedList<ValidationResult>()

    open internal fun loadData(): Properties {
        val data = Properties()
        data.load(file.reader())
        return data
    }

    abstract fun createValidators():
            Map<String,List<IPropertiesFileValueValidator>>
}