/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import java.io.File
import java.util.*

/**
 * Created by pisarenko on 16.05.2016.
 */
open class PropertiesFileSimParametersProviderWithPredefinedData(
        val prop: Properties
) : PropertiesFileSimParametersProvider(File("someFile")) {
    override fun createValidators(): Map<String,
            List<IPropertiesFileValueValidator>> = emptyMap()
    override fun loadData():Properties = prop
}