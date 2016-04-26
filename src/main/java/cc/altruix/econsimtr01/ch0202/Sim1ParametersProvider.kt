package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ISimParametersProvider
import cc.altruix.econsimtr01.PlFlow
import cc.altruix.econsimtr01.PlTransformation
import cc.altruix.econsimtr01.ch0201.InfiniteResourceSupply
import cc.altruix.econsimtr01.ch0201.InitialResourceLevel

/**
 * Created by pisarenko on 26.04.2016.
 */
class Sim1ParametersProvider(override val agents: MutableList<IAgent>,
                             override val flows: MutableList<PlFlow>,
                             override val initialResourceLevels: MutableList<InitialResourceLevel>,
                             override val infiniteResourceSupplies: MutableList<InfiniteResourceSupply>,
                             override val transformations: MutableList<PlTransformation>) : ISimParametersProvider {
}