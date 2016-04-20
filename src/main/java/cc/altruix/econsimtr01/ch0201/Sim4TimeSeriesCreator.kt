package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.DefaultTimeSeriesCreator
import cc.altruix.econsimtr01.DefaultTimeSeriesCreator2
import java.io.File

/**
 * Created by pisarenko on 19.04.2016.
 */
class Sim4TimeSeriesCreator(
        columns:Array<ColumnDescriptor> =
        arrayOf(
                ColumnDescriptor(
                        "t [min]",
                        TimeSecondsColFunction()
                ),
                ColumnDescriptor(
                        "Time",
                        TimeLongFormColFunction()
                )
        )) : DefaultTimeSeriesCreator2(columns) {
    // TODO: Add column: "Amount in savings account"
    // TODO: Add column: "People in the list"
    // TODO: Add column: "Software completion (percentage of 480 hours)"
    override fun prologToCsv(input: File): String {
        // TODO: Implement this
        // TODO: Test this
        throw UnsupportedOperationException()
    }
}