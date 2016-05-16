/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

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

    }
    override open fun initListRelatedFlows(agents: List<IAgent>, flws: List<PlFlow>) { }

    override open fun initFlowSubscriptions() { }

    override open fun createTransformation(res: SolveInfo): PlTransformation {
        val id = extractId(res)

        if (id == "t1") {
            return T1(id,
                    extractAgent(res, "Agent"),
                    extractAmount(res, "InputAmount") ?: 0.0,
                    extractResource(res, "InputResource"),
                    extractAmount(res, "OutputAmount") ?: 0.0,
                    extractResource(res, "OutputResource"),
                    extractFiringFunction(res))
        }

        return PlTransformation(
                id,
                extractAgent(res, "Agent"),
                extractAmount(res, "InputAmount") ?: 0.0,
                extractResource(res, "InputResource"),
                extractAmount(res, "OutputAmount") ?: 0.0,
                extractResource(res, "OutputResource"),
                extractFiringFunction(res)
        )
    }

}