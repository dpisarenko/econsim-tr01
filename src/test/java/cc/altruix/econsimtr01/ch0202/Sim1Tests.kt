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

package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.PlFlow
import cc.altruix.econsimtr01.PlTransformation
import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.ch0201.InfiniteResourceSupply
import cc.altruix.econsimtr01.ch0201.InitialResourceLevel
import org.fest.assertions.Assertions
import org.junit.Test
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Sim1Tests {
    @Test
    fun createAgents() {
        val agents = LinkedList<IAgent>()
        val flows =  LinkedList<PlFlow>()
        val initialResourceLevels =
                LinkedList<InitialResourceLevel>()
        val infiniteResourceSupplies =
                LinkedList<InfiniteResourceSupply>()
        val transformations =
                LinkedList<PlTransformation>()
        val out = Sim1(StringBuilder(),
                LinkedList<ResourceFlow>(),
                Sim1ParametersProvider(agents,
                        flows,
                        initialResourceLevels,
                        infiniteResourceSupplies,
                        transformations))
        val actResult = out.createAgents()
        Assertions.assertThat(actResult).isNotNull
        Assertions.assertThat(actResult.size).isEqualTo(2)
        Assertions.assertThat(actResult.get(0) is Protagonist).isTrue()
    }
}
