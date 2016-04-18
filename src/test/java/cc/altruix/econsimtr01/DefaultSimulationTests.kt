package cc.altruix.econsimtr01

import cc.altruix.econsimtr01.ch0201.InfiniteResourceSupply
import cc.altruix.econsimtr01.ch0201.InitialResourceLevel
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Ignore
import org.junit.Test
import java.util.*

/**
 * Created by pisarenko on 18.04.2016.
 */
class DefaultSimulationTests {
    class SimParametersProvider(override val agents: MutableList<IAgent>,
                                override val flows: MutableList<PlFlow>,
                                override val initialResourceLevels: MutableList<InitialResourceLevel>,
                                override val infiniteResourceSupplies: MutableList<InfiniteResourceSupply>) :
            ISimParametersProvider {

    }
    class DefaultSimulationForTesting : DefaultSimulation(
            SimParametersProvider(
                    LinkedList<IAgent>(),
                    LinkedList<PlFlow>(),
                    LinkedList<InitialResourceLevel>(),
                    LinkedList<InfiniteResourceSupply>()
            )
    ) {
        override fun continueCondition(tick: DateTime): Boolean = true
        override fun createAgents(): List<IAgent> = emptyList()
        override fun createSensors(): List<ISensor> = emptyList()
    }

    @Test
    @Ignore
    fun runTicksEveryMinute() {
        val out = DefaultSimulationForTesting()
        val t0 = 0L.millisToSimulationDateTime()
        val act1 = out.minimalSimulationCycle(emptyList(), emptyList(), t0)
        val t1 = t0.plusMinutes(1)
        Assertions.assertThat(act1).isEqualTo(t1)

    }
}