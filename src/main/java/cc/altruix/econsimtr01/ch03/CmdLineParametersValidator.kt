package cc.altruix.econsimtr01.ch03

import java.io.File

/**
 * Created by pisarenko on 14.05.2016.
 */
open class CmdLineParametersValidator : ICmdLineParametersValidator {
    companion object {
        val USAGE = "Usage: java -cp java -cp econsim-tr01-1.0-SNAPSHOT-jar-with-dependencies.jar cc.altruix.econsimtr01.ch03.BasicAgriculturalSimulationAppKt sim1.properties [sim2.properties...]"
    }
    var simParamProviders = emptyList<PropertiesFileSimParametersProvider>()
    override fun validate(args: Array<String>): ValidationResult {
        if (args.isEmpty()) {
            return ValidationResult(
                    valid = false,
                    message = USAGE
            )
        }
        val files = args.map { createFile(it) }
        val unreadableFile = files.filter { !canRead(it) }.firstOrNull()

        if (unreadableFile != null) {
            return ValidationResult(
                    valid = false,
                    message = "Can't read file '${unreadableFile.name}'"
            )
        }
        simParamProviders = files.map { createSimParametersProvider(it) }
        simParamProviders.forEach { it.initAndValidate() }
        val invalidParamProvider = simParamProviders.filter { !it.validity.valid }.firstOrNull()
        if (invalidParamProvider != null) {
            return ValidationResult(
                    valid = false,
                    message = "File '${invalidParamProvider.file.name}' is invalid ('${invalidParamProvider.validity.message}')"
            )
        }
        return ValidationResult(true, "")
    }
    internal open fun createFile(name:String):File = File(name)
    internal open fun canRead(file: File) : Boolean = file.canRead()
    internal open fun createSimParametersProvider(file:File) : PropertiesFileSimParametersProvider =
            AgriculturalSimParametersProvider(file)
}