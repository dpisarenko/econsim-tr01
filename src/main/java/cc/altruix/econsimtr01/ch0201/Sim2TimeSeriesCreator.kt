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
class Sim2TimeSeriesCreator : DefaultTimeSeriesCreator() {
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
                ColumnDescriptor("Subscribers (7 or more interactions)", SubscribersCohortColFunction(7))
            )
        )
    }



    override fun prologToCsv(input: File): String {
        val builder = StringBuilder()
        appendRow(builder, columns)

        val prolog = PlUtils.createEngine()
        val times = extractTimes(input, prolog)

        times.forEach { t ->
            val data = calculateData(prolog, t, columns)
            appendRow(builder, data)
        }

        return builder.toString()
    }

    private fun calculateData(prolog: Prolog, t: Long, columns: Array<String>): Array<String> {
        // TODO: Test this
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
