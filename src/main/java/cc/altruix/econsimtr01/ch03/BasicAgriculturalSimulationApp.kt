package cc.altruix.econsimtr01.ch03

import java.io.PrintStream

/**
 * Created by pisarenko on 13.05.2016.
 */
class BasicAgriculturalSimulationApp(
        val cmdLineParamValidator:ICmdLineParametersValidator = CmdLineParametersValidator()
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
    }
}
fun main(args : Array<String>) {
    BasicAgriculturalSimulationApp().run(args, System.out, System.err)
    println("Basic agriculture simulation")
    println("(C) Copyright 2016 Dmitri Pisarenko")
}
