/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

/**
 * Created by pisarenko on 15.05.2016.
 */
interface ISemanticSimulationParametersValidator {
    fun validate(scenario:PropertiesFileSimParametersProvider):ValidationResult
}