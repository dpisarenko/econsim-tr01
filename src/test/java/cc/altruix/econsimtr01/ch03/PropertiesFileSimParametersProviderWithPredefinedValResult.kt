/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 */

package cc.altruix.econsimtr01.ch03

import java.io.File

/**
 * Created by pisarenko on 14.05.2016.
 */
open class PropertiesFileSimParametersProviderWithPredefinedValResult(file: File,
                                                                      val valResult: ValidationResult) :
        PropertiesFileSimParametersProvider(file) {
    override fun createValidators(): Map<String, List<IPropertiesFileValueValidator>> = emptyMap()
    override fun initAndValidate() {
        validity = valResult
    }
}