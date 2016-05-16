/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
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