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

import cc.altruix.econsimtr01.CmdLineParametersValidator
import java.io.File

/**
 * Created by pisarenko on 14.05.2016.
 */
open class AgrigulturalSimulationCmdLineParametersValidator :
    CmdLineParametersValidator(USAGE) {
    companion object {
        val USAGE = "Usage: java -cp java -cp econsim-tr01-1.0-SNAPSHOT-jar-with-dependencies.jar cc.altruix.econsimtr01.ch03.BasicAgriculturalSimulationAppKt sim1.properties [sim2.properties...]"
    }

    override open fun createSimParametersProvider(file:File) :
        PropertiesFileSimParametersProvider =
            AgriculturalSimParametersProvider(file)
}