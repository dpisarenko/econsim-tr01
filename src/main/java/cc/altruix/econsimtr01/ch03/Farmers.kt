/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.DefaultAgent

/**
 * Created by pisarenko on 16.05.2016.
 */
class Farmers : DefaultAgent("Farmers") {
    companion object {
        val ID = "Farmers"
    }
    init {
        actions.add(Process1())
        actions.add(Process3())
    }
    // TODO: Implement process #1 (putting seeds into ground)
    // TODO: Implement process #3 (collect the harvest)
}