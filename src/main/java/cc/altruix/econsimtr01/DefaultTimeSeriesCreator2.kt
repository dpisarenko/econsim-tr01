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

package cc.altruix.econsimtr01

import cc.altruix.econsimtr01.ch0201.ColumnDescriptor
import java.io.File

/**
 * Created by pisarenko on 20.04.2016.
 */
abstract class DefaultTimeSeriesCreator2(val columns:Array<ColumnDescriptor>) : DefaultTimeSeriesCreator() {
    override fun prologToCsv(input: File): String {
        val builder = createStringBuilder()
        appendHeader(builder, columns)
        val prolog = createPrologEngine()
        val times = extractTimes(input, prolog)
        appendRows(builder, prolog, times, columns)
        return builder.toString()
    }

}