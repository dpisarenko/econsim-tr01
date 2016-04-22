package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.DefaultTimeSeriesCreator2

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
                ),
                ColumnDescriptor(
                        "Software completion [%]",
                        PercentageResourceLevelColFunction("stacy", "r14", 480.0)
                ),
                ColumnDescriptor(
                        "Ready for transition? [yes/no]",
                        TransitionReadinessColFunction()
                )
        )) : DefaultTimeSeriesCreator2(columns) {
}