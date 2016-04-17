package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.DefaultTimeSeriesCreator
import cc.altruix.javaprologinterop.PlUtils
import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
open class Sim2TimeSeriesCreator : DefaultTimeSeriesCreator() {
    companion object {
        val columns = arrayOf(
                ColumnDescriptor("t [sec]", TimeSecondsColFunction()),
                ColumnDescriptor("Time", TimeLongFormColFunction()),
                ColumnDescriptor("Money @ Stacy", MoneyAtStacyColFunction()),
                ColumnDescriptor("Copies of software @ Target audience", CopiesOfSoftwareAtTargetAudienceColFunction()),
                ColumnDescriptor("Total number of subscribers in the list", TotalNumberOfSubscribersInListColFunction()),
                ColumnDescriptor("Subscribers (1 interaction)", SubscribersCohortColFunction(1)),
                ColumnDescriptor("Subscribers (2 interactions)", SubscribersCohortColFunction(2)),
                ColumnDescriptor("Subscribers (3 interactions)", SubscribersCohortColFunction(3)),
                ColumnDescriptor("Subscribers (4 interactions)", SubscribersCohortColFunction(4)),
                ColumnDescriptor("Subscribers (5 interactions)", SubscribersCohortColFunction(5)),
                ColumnDescriptor("Subscribers (6 interactions)", SubscribersCohortColFunction(6)),
                ColumnDescriptor("Subscribers (7 or more interactions)", SubscribersCohortColFunction(7)
                )
        )
    }

    override fun prologToCsv(input: File): String {
        val builder = createStringBuilder()
        appendHeader(builder, columns)
        val prolog = createPrologEngine()
        val times = extractTimes(input, prolog)
        appendRows(builder, prolog, times, columns)
        return builder.toString()
    }

    open internal fun createStringBuilder() = StringBuilder()

    open internal fun appendRows(builder: StringBuilder,
                             prolog: Prolog,
                             times: List<Long>,
                             columns: Array<ColumnDescriptor>) {
        // TODO: Test this
        times.forEach { t ->
            val data = calculateData(prolog, t, columns)
            appendRow(builder, data)
        }
    }

    open internal fun createPrologEngine() = PlUtils.createEngine()

    open internal fun appendHeader(builder: StringBuilder, cols: Array<ColumnDescriptor>) {
        // TODO: Test this
        appendRow(builder, cols.map { it.title }.toTypedArray())
    }

    private fun calculateData(prolog: Prolog, t: Long, columns: Array<ColumnDescriptor>): Array<String> {
        // TODO: Test this
        return columns.map { it.func(prolog, t) }.toTypedArray()
    }
}
