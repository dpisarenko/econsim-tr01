package cc.altruix.econsimtr01.ch03

import java.io.File

/**
 * Created by pisarenko on 14.05.2016.
 */
open class CmdLineParametersValidator : ICmdLineParametersValidator {
    companion object {
        val USAGE = "Usage: java -cp java -cp econsim-tr01-1.0-SNAPSHOT-jar-with-dependencies.jar cc.altruix.econsimtr01.ch03.BasicAgriculturalSimulationAppKt sim1.properties [sim2.properties...]"
    }
    override fun validate(args: Array<String>): CmdLineParametersValidationResult {
        if (args.isEmpty()) {
            return CmdLineParametersValidationResult(
                    valid = false,
                    message = USAGE
            )
        }
        val files = args.map { createFile(it) }
        val unreadableFile = files.filter { !it.canRead() }.firstOrNull()

        if (unreadableFile != null) {
            return CmdLineParametersValidationResult(
                    valid = false,
                    message = "Can't read file '${unreadableFile.name}'"
            )
        }

        // TODO: Test this
        // TODO: Implement this
        throw UnsupportedOperationException()
    }
    internal open fun createFile(name:String):File = File(name)
    internal open fun canRead(file: File) : Boolean = file.canRead()
}