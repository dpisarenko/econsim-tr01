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

import cc.altruix.econsimtr01.ch03.ICmdLineParametersValidator
import cc.altruix.econsimtr01.ch03.PropertiesFileSimParametersProvider
import cc.altruix.econsimtr01.ch03.ValidationResult
import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
abstract class CmdLineParametersValidator(val usageStatement:String) :
    ICmdLineParametersValidator {
    override var simParamProviders =
        emptyList<PropertiesFileSimParametersProvider>()
    override fun validate(args: Array<String>): ValidationResult {
        if (args.isEmpty()) {
            return ValidationResult(
                valid = false,
                message = usageStatement
            )
        }
        val files = args.map { createFile(it) }
        val unreadableFile = files.filter { !canRead(it) }.firstOrNull()

        if (unreadableFile != null) {
            return ValidationResult(
                valid = false,
                message = "Can't read file '${unreadableFile.name}'"
            )
        }
        simParamProviders = files.map { createSimParametersProvider(it) }
        simParamProviders.forEach { it.initAndValidate() }
        val invalidParamProvider = simParamProviders
            .filter { !it.validity.valid }
            .firstOrNull()
        if (invalidParamProvider != null) {
            return ValidationResult(
                valid = false,
                message = "File '${invalidParamProvider.file.name}' is invalid ('${invalidParamProvider.validity.message}')"
            )
        }
        return ValidationResult(true, "")
    }
    internal open fun createFile(name:String): File = File(name)
    internal open fun canRead(file: File) : Boolean = file.canRead()
    abstract fun createSimParametersProvider(file: File) :
        PropertiesFileSimParametersProvider

}
