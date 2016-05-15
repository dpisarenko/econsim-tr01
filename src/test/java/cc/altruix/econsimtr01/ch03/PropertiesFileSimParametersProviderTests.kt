package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.mock
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
        val validator1 = mock<IPropertiesFileValueValidator>()
        val validator2 = mock<IPropertiesFileValueValidator>()
        val validator3 = mock<IPropertiesFileValueValidator>()
        val param1Validators = listOf(validator1, validator2)
        val param2Validators = listOf(validator3)
        validators["Param1"] = param1Validators
        validators["Param2"] = param2Validators
        val valResults: MutableList<ValidationResult> = mock()
        Mockito.doReturn(validators).`when`(out).createValidators()
        Mockito.doReturn(valid).`when`(out).calculateValidity(valResults)
        val message = "message"
        Mockito.doReturn(message).`when`(out).createMessage(valResults, valid)
        // Run method under test
        out.initAndValidate()
        // Verify
        Mockito.verify(out).createValidators()
        Mockito.verify(out).loadData()
        Mockito.verify(out).createValResults()
        Mockito.verify(out).applyValidators(data, valResults, "Param1", param1Validators)
        Mockito.verify(out).applyValidators(data, valResults, "Param2", param2Validators)
        Mockito.verify(out).calculateValidity(valResults)
        Mockito.verify(out).createMessage(valResults, valid)
        Assertions.assertThat(out.validity.valid).isEqualTo(valid)
        Assertions.assertThat(out.validity.message).isEqualTo(message)
    }
}