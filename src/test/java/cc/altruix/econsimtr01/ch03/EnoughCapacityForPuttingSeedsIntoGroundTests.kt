package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.io.File

/**
 * Created by pisarenko on 16.05.2016.
 */
class EnoughCapacityForPuttingSeedsIntoGroundTests {
    @Test
    fun validateWiring() {
        validateWiringTestLogic(
                totalRequiredEffort = 1.0,
                availableWorkingTime = 2.0,
                expectedValidity = true,
                expectedMessage = ""
        )
        validateWiringTestLogic(
                totalRequiredEffort = 2.0,
                availableWorkingTime = 1.0,
                expectedValidity = false,
                expectedMessage = "Process 1: It requires 2.0 hours of effort, but only 1.0 is available"
        )
    }
    private fun validateWiringTestLogic(totalRequiredEffort:Double,
                                        availableWorkingTime:Double,
                                        expectedValidity:Boolean,
                                        expectedMessage:String) {
        // Prepare
        val out = Mockito.spy(EnoughCapacityForPuttingSeedsIntoGround())
        val scenario = PropertiesFileSimParametersProviderForTesting(File("someFile"))
        Mockito.doReturn(totalRequiredEffort).`when`(out).calculateTotalRequiredEffort(scenario)
        Mockito.doReturn(availableWorkingTime).`when`(out).calculateAvailableWorkingTime(scenario)
        // Run method under test
        val res = out.validate(scenario)
        // Verify
        Assertions.assertThat(res.valid).isEqualTo(expectedValidity)
        Assertions.assertThat(res.message).isEqualTo(expectedMessage)
    }
}