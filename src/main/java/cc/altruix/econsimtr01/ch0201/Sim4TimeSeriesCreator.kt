package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.DefaultTimeSeriesCreator
import cc.altruix.econsimtr01.DefaultTimeSeriesCreator2
import java.io.File

/**
 * Created by pisarenko on 19.04.2016.
 */
class Sim4TimeSeriesCreator(
        columns:Array<ColumnDescriptor> =
        arrayOf(/*TODO: Test this*/
                ColumnDescriptor(
                        "t [min]",
                        TimeSecondsColFunction()
                ),
                ColumnDescriptor(
                        "Time",
                        TimeLongFormColFunction()
                ),
                ColumnDescriptor(
                        "Money in savings account",
                        MoneyInSavingsAccountColFunction()
                ),
                ColumnDescriptor(
                        "Subscribers in the list (1 interaction)",
                        SubscribersCohortColFunction(1)
                ),
                ColumnDescriptor(
                        "Software completion [hours spent]",
                        AbsoluteResourceLevelColFunction("stacy", "r14")
                )

        )) : DefaultTimeSeriesCreator2(columns) {
    // TODO: Add column: "Software completion (percentage of 480 hours)"
}