package cc.altruix.econsimtr01.ch03

import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito
import java.io.File

/**
 * Created by pisarenko on 14.05.2016.
 */
class CmdLineParametersValidatorTests {
    @Test
    fun validateReturnsFalseOnEmptyArray() {
        val out = CmdLineParametersValidator()
        val res = out.validate(emptyArray())
        Assertions.assertThat(res).isNotNull
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo(CmdLineParametersValidator.USAGE)
    }
    @Test
    fun validateReturnsFalseIfFileNotReadable() {
        val fname1 = "fname1"
        val fname2 = "fname2"
        val args = arrayOf(fname1, fname2)
        val file1 = File(fname1)
        val file2 = File(fname2)
        val out = Mockito.spy(CmdLineParametersValidator())
        Mockito.doReturn(file1).`when`(out).createFile(fname1)
        Mockito.doReturn(file2).`when`(out).createFile(fname2)
        Mockito.doReturn(true).`when`(out).canRead(file1)
        Mockito.doReturn(false).`when`(out).canRead(file2)
        // Run method under test
        val res = out.validate(args)
        // Verify
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo("Can't read file '$fname2'")
        Mockito.verify(out).createFile(fname1)
        Mockito.verify(out).createFile(fname2)
        Mockito.verify(out).canRead(file1)
        Mockito.verify(out).canRead(file2)
    }
    @Test
    fun validateDetectsFilesWithMissingOrIncorrectData() {
        // Prepare
        val fname1 = "fname1"
        val fname2 = "fname2"
        val args = arrayOf(fname1, fname2)
        val file1 = File(fname1)
        val file2 = File(fname2)
        val out = Mockito.spy(CmdLineParametersValidator())
        Mockito.doReturn(file1).`when`(out).createFile(fname1)
        Mockito.doReturn(file2).`when`(out).createFile(fname2)
        Mockito.doReturn(true).`when`(out).canRead(file1)
        Mockito.doReturn(true).`when`(out).canRead(file2)
        val simParamProv1Validity = ValidationResult(true, "")
        val simParamProv2Validity = ValidationResult(false, "SimParametersProviderError")

        val simParamProv1 = Mockito.spy(PropertiesFileSimParametersProviderForTesting(file1, simParamProv1Validity))
        val simParamProv2 = Mockito.spy(PropertiesFileSimParametersProviderForTesting(file2, simParamProv2Validity))
        Mockito.doReturn(simParamProv1).`when`(out).createSimParametersProvider(file1)
        Mockito.doReturn(simParamProv2).`when`(out).createSimParametersProvider(file2)
        // Run method under test
        val res = out.validate(args)
        // Verify
        Assertions.assertThat(res.valid).isFalse()
        Assertions.assertThat(res.message).isEqualTo("File 'fname2' is invalid ('SimParametersProviderError')")
        Mockito.verify(out).createFile(fname1)
        Mockito.verify(out).createFile(fname2)
        Mockito.verify(out).canRead(file1)
        Mockito.verify(out).canRead(file2)
        Mockito.verify(out).createSimParametersProvider(file1)
        Mockito.verify(out).createSimParametersProvider(file2)
        Mockito.verify(simParamProv1).initAndValidate()
        Mockito.verify(simParamProv2).initAndValidate()
    }
    @Test
    fun validateReturnsTrueIfEverythingIsCorrect() {
        // Prepare
        val fname1 = "fname1"
        val fname2 = "fname2"
        val args = arrayOf(fname1, fname2)
        val file1 = File(fname1)
        val file2 = File(fname2)
        val out = Mockito.spy(CmdLineParametersValidator())
        Mockito.doReturn(file1).`when`(out).createFile(fname1)
        Mockito.doReturn(file2).`when`(out).createFile(fname2)
        Mockito.doReturn(true).`when`(out).canRead(file1)
        Mockito.doReturn(true).`when`(out).canRead(file2)
        val simParamProv1Validity = ValidationResult(true, "")
        val simParamProv2Validity = ValidationResult(true, "SimParametersProviderError")
        val simParamProv1 = Mockito.spy(PropertiesFileSimParametersProviderForTesting(file1, simParamProv1Validity))
        val simParamProv2 = Mockito.spy(PropertiesFileSimParametersProviderForTesting(file2, simParamProv2Validity))
        Mockito.doReturn(simParamProv1).`when`(out).createSimParametersProvider(file1)
        Mockito.doReturn(simParamProv2).`when`(out).createSimParametersProvider(file2)
        // Run method under test
        val res = out.validate(args)
        // Verify
        Assertions.assertThat(res.valid).isTrue()
        Assertions.assertThat(res.message).isEqualTo("")
        Mockito.verify(out).createFile(fname1)
        Mockito.verify(out).createFile(fname2)
        Mockito.verify(out).canRead(file1)
        Mockito.verify(out).canRead(file2)
        Mockito.verify(out).createSimParametersProvider(file1)
        Mockito.verify(out).createSimParametersProvider(file2)
        Mockito.verify(simParamProv1).initAndValidate()
        Mockito.verify(simParamProv2).initAndValidate()
    }
}