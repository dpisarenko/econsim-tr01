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

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.simulationRunLogic
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 08.04.2016.
 */
class Sim1Tests {
    @Test
    //@Ignore
    fun test() {
        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim1ParametersProvider(
                File("src/test/resources/ch0201Sim1Tests.params.pl").readText()
        )
        val sim = Sim1(
                log,
                flows,
                simParametersProvider
        )
        simulationRunLogic(sim,
                log,
                simParametersProvider.resources,
                flows,
                "src/test/resources/ch0201/Sim1Tests.test.pl.expected.txt",
                "src/test/resources/ch0201/Sim1Tests.test.csv.expected.txt",
                "src/test/resources/ch0201/Sim1Tests.test.flows.actual.png",
                Sim1TimeSeriesCreator()
        )
    }
}
