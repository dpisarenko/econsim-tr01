package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 15.05.2016.
 */
class PropertiesFileSimParametersProviderTests {
    @Test
    fun initAndValidateWiring() {
        initAndValidateWiringTestLogic(true)
        initAndValidateWiringTestLogic(false)
    }

    private fun initAndValidateWiringTestLogic(valid: Boolean) {
        // Prepare
        val out = Mockito.spy(PropertiesFileSimParametersProviderForTesting(File("someFile")))
        val data = Properties()
        Mockito.doReturn(data).`when`(out).loadData()
        val validators: MutableMap<String, List<IPropertiesFileValueValidator>> = HashMap()
        val validator1 = ExistenceValidator
        val validator2 = NonBlankStringValidator
        val validator3 = ExistenceValidator
        val param1Validators = listOf(validator1, validator2)
        val param2Validators = listOf(validator3)
        validators["Param1"] = param1Validators
        validators["Param2"] = param2Validators
        val valResults: MutableList<ValidationResult> = LinkedList()
        Mockito.doReturn(validators).`when`(out).createValidators()
        Mockito.doReturn(valid).`when`(out).calculateValidity(valResults)
        val message = "message"
        Mockito.doReturn(message).`when`(out).createMessage(valResults, valid)
        Mockito.doNothing().`when`(out).applyValidators(data, valResults, "Param1", param1Validators)
        Mockito.doNothing().`when`(out).applyValidators(data, valResults, "Param2", param2Validators)
        // Run method under test
        out.initAndValidate()
        // Verify
        Mockito.verify(out).createValidators()
        Mockito.verify(out).loadData()
        Mockito.verify(out).createValResults()
        Mockito.verify(out).applyValidators(data, valResults, "Param2", param2Validators)
        Mockito.verify(out).applyValidators(data, valResults, "Param1", param1Validators)
        Mockito.verify(out).calculateValidity(valResults)
        Mockito.verify(out).createMessage(valResults, valid)
        Assertions.assertThat(out.validity.valid).isEqualTo(valid)
        Assertions.assertThat(out.validity.message).isEqualTo(message)
    }
    @Test
    fun createMessageCreatesEmptyMessageIfEverythingValid() {
        // Prepare
        val out = PropertiesFileSimParametersProviderForTesting(File("someFile"))
        val valResults = listOf<ValidationResult>(
                ValidationResult(true, "message 1"),
                ValidationResult(false, "message 2")
        )
        // Run method under test
        val res = out.createMessage(valResults, true)
        // Verify
        Assertions.assertThat(res).isEmpty()
    }
    @Test
    fun createMessageCreatesRightMessageIfInvalid() {
        // Prepare
        val out = PropertiesFileSimParametersProviderForTesting(File("someFile"))
        val valResults = listOf<ValidationResult>(
                ValidationResult(true, "message 1"),
                ValidationResult(false, "message 2"),
                ValidationResult(false, "message 3")
        )
        // Run method under test
        val res = out.createMessage(valResults, false)
        // Verify
        Assertions.assertThat(res).isEqualTo("message 2, message 3")
    }
    @Test
    fun calculateValidity() {
        calculateValidityTestLogic(listOf(
                ValidationResult(true, "message 1"),
                ValidationResult(false, "message 2"),
                ValidationResult(false, "message 3")
        ), false)
        calculateValidityTestLogic(listOf(
                ValidationResult(true, "message 1"),
                ValidationResult(true, "message 2"),
                ValidationResult(true, "message 3")
        ), true)
    }

    private fun calculateValidityTestLogic(valResults: List<ValidationResult>,
                                           expectedResult: Boolean) {
        val out = PropertiesFileSimParametersProviderForTesting(File("someFile"))
        val valResults2 = valResults
        Assertions.assertThat(out.calculateValidity(valResults2)).isEqualTo(expectedResult)
    }
}