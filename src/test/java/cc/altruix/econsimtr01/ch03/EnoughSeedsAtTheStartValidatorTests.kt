/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.junit.Test
import java.util.*

/**
 * Created by pisarenko on 16.05.2016.
 */
class EnoughSeedsAtTheStartValidatorTests {
    @Test
    fun validate() {
        validateTestLogic(
                sizeOfField = "250000",
                seedsPerSquareMeter = "0.0629",
                initialSeedQuantity = "15725.0",
                expectedValidity = true,
                expectedMessage = "")
        validateTestLogic(
                sizeOfField = "250000",
                seedsPerSquareMeter = "0.0449",
                initialSeedQuantity = "11225.0",
                expectedValidity = true,
                expectedMessage = "")

        validateTestLogic(
                sizeOfField = "250000",
                seedsPerSquareMeter = "0.0629",
                initialSeedQuantity = "15724.9",
                expectedValidity = false,
                expectedMessage =
                    "We need 15725.0 kg of seeds, but only have 15724.9"
        )

    }

    private fun validateTestLogic(
            sizeOfField: String,
            seedsPerSquareMeter: String,
            initialSeedQuantity: String,
            expectedValidity: Boolean,
            expectedMessage: String) {
        // Prepare
        val data = Properties()
        data["SizeOfField"] = sizeOfField
        data["Process1QuantityOfSeeds"] = seedsPerSquareMeter
        data["InitialSeedQuantity"] = initialSeedQuantity
        val scenario =
                PropertiesFileSimParametersProviderWithPredefinedData(data)
        scenario.initAndValidate()
        val out = EnoughSeedsAtTheStartValidator()
        // Run method under test
        val res = out.validate(scenario)
        // Verify
        Assertions.assertThat(res.message).isEqualTo(expectedMessage)
        Assertions.assertThat(res.valid).isEqualTo(expectedValidity)
    }
}