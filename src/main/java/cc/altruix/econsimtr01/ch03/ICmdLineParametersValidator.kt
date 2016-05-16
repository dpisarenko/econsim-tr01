/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

/**
 * Created by pisarenko on 14.05.2016.
 */
interface ICmdLineParametersValidator {
    fun validate(args:Array<String>): ValidationResult
}