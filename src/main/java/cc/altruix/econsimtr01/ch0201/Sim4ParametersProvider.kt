package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import alice.tuprolog.SolveInfo
import cc.altruix.econsimtr01.*

/**
 * Created by pisarenko on 19.04.2016.
 */
open class Sim4ParametersProvider(theoryTxt2:String) :
        Sim2ParametersProvider(theoryTxt2) {
    override fun createFlow(res: SolveInfo, prolog: Prolog): PlFlow =
            super.createFlow(extractFlowData(res))
    override fun readAgents(prolog: Prolog) {
        val agentsPl = prolog.getResults("isAgent(X).", "X")
        agentsPl
                .map { x -> x.removeSingleQuotes() }
                .map { DefaultAgent(it) }
                .forEach { this.agents.add(it) }

        // TODO: Validate that all transformations have been added to the agents.
        // TODO: Test this (start)
        this.transformations.forEach {
            val agent = findAgent(it.agentId, this.agents)
            if ((agent != null) && (agent is DefaultAgent)) {
                agent.addTransformation(it)
            }
        }
        // TODO: Test this (start)
    }
    override open fun initListRelatedFlows(agents: List<IAgent>, flws: List<PlFlow>) { }

    override open fun initFlowSubscriptions() { }
}