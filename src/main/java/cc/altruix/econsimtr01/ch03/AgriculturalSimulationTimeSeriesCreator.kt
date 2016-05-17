package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.TimeSeriesCreator
import cc.altruix.econsimtr01.ch0202.SimResRow
import org.joda.time.DateTime

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class AgriculturalSimulationTimeSeriesCreator(simData: Map<DateTime, SimResRow<AgriculturalSimulationRowField>>,
                                              targetFileName: String,
                                              simNames: List<String>) :
        TimeSeriesCreator<AgriculturalSimulationRowField>(
                simData,
                targetFileName,
                simNames,
                AgriculturalSimulationRowField.values()) {

}
