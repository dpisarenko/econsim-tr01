package cc.altruix.econsimtr01.ch03

import java.io.PrintStream
import java.util.*

/**
 * Created by pisarenko on 13.05.2016.
 */
class BasicAgriculturalSimulationApp(
        val cmdLineParamValidator:CmdLineParametersValidator = CmdLineParametersValidator()
) {
    fun run(args: Array<String>,
            out: PrintStream,
            err: PrintStream) {
        // TODO: Test this
        // TODO: Implement process #1
        // TODO: Implement process #2
        // TODO: Implement process #3
        val cmdLineParamValRes = cmdLineParamValidator.validate(args)
        if (!cmdLineParamValRes.valid) {
            err.println(cmdLineParamValRes.message)
            return
        }
        val validators = createSemanticValidators()
        val scenarios = cmdLineParamValidator.simParamProviders
        val valRes = LinkedList<ValidationResult>()
        scenarios.forEach { scenario ->
            validators.map { it.validate(scenario) }.forEach { valRes.add(it) }
        }
        val error = valRes.find { it.valid == false }
        if (error != null) {
            val allErrors = valRes.filter { it.valid == false }.map { it.message }.joinToString (
                    separator = ", "
            )
            err.println("One or more scenarios are invalid:")
            err.println(allErrors)
            return
        }

        //cmdLineParamValidator.simParamProviders.forEach { scenario -> }
    }
    fun createSemanticValidators():List<ISemanticSimulationParametersValidator> =
            listOf(
                    EnoughCapacityForPuttingGroundsIntoField,
                    EnoughCapacityForHarvesting
            )
}
fun main(args : Array<String>) {
    BasicAgriculturalSimulationApp().run(args, System.out, System.err)
    println("Basic agriculture simulation")
    println("(C) Copyright 2016 Dmitri Pisarenko")
}
