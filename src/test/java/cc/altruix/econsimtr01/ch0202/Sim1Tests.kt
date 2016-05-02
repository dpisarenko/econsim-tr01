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
        Assertions.assertThat(actResult.get(1) is Population).isTrue()
    }
}
