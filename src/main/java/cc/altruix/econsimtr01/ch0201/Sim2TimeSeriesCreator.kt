/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
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
