/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.DefaultAgent

/**
 * Created by pisarenko on 16.05.2016.
 */
class Field : DefaultAgent(ID) {
    companion object {
        val ID = "Field"
    }
    init {
        actions.add(Process2())
    }
    // TODO: Implement process #2 (ripen)
}