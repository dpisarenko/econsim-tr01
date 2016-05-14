package cc.altruix.econsimtr01.ch03

/**
 * Created by pisarenko on 14.05.2016.
 */
class CmdLineParametersValidator : ICmdLineParametersValidator {
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
        // TODO: Test this
        // TODO: Implement this
        throw UnsupportedOperationException()
    }
}