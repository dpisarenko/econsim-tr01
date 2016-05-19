/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 */

package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.DefaultTimeSeriesCreator2

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
open class Sim2TimeSeriesCreator(
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
                        "Money @ Stacy",
                        MoneyAtStacyColFunction()
                ),
                ColumnDescriptor(
                        "Copies of software @ Target audience",
                        CopiesOfSoftwareAtTargetAudienceColFunction()
                ),
                ColumnDescriptor(
                        "Total number of subscribers in the list",
                        TotalNumberOfSubscribersInListColFunction()
                ),
                ColumnDescriptor(
                        "Subscribers (1 interaction)",
                        SubscribersCohortColFunction(1)
                ),
                ColumnDescriptor(
                        "Subscribers (2 interactions)",
                        SubscribersCohortColFunction(2)
                ),
                ColumnDescriptor(
                        "Subscribers (3 interactions)",
                        SubscribersCohortColFunction(3)
                ),
                ColumnDescriptor(
                        "Subscribers (4 interactions)",
                        SubscribersCohortColFunction(4)
                ),
                ColumnDescriptor(
                        "Subscribers (5 interactions)",
                        SubscribersCohortColFunction(5)
                ),
                ColumnDescriptor(
                        "Subscribers (6 interactions)",
                        SubscribersCohortColFunction(6)
                ),
                ColumnDescriptor(
                        "Subscribers (7 or more interactions)",
                        SubscribersCohortColFunction(7)
                )
        )
) : DefaultTimeSeriesCreator2(columns)
