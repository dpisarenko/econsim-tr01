/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import java.util.*

/**
 * Created by pisarenko on 14.05.2016.
 */
interface IPropertiesFileValueValidator {
    fun validate(data: Properties, param:String):ValidationResult
}