/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch0201

/**
 * Created by pisarenko on 14.04.2016.
 */
data class Subscriber(val id:String,
                      var interactionsWithStacy:Int = 0,
                      var boughtSomething : Boolean = false)