package cc.altruix.econsimtr01.ch03

import java.util.*

/**
 * Created by pisarenko on 14.05.2016.
 */
object DayOfMonthValidator : IPropertiesFileValueValidator {
    override fun validate(data: Properties, param:String): ValidationResult {
        val pvalue = data[param].toString()

        val tokenizer = StringTokenizer(pvalue, ".")

        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }
}