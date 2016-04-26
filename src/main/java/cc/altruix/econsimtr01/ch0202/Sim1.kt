package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.DefaultSimulation
import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ISensor
import cc.altruix.econsimtr01.ResourceFlow
import org.joda.time.DateTime

/**
 * Created by pisarenko on 26.04.2016.
 */
class Sim1(val logTarget:StringBuilder,
           val flows:MutableList<ResourceFlow>,
           simParametersProvider: Sim1ParametersProvider) : DefaultSimulation(simParametersProvider) {
    companion object {
        /**
         * OFFLINE_NETWORKING_INTENSITY
         * Unit: People per week
         *
         * Number of people, which he didn't previously know, and
         * which he meets every week.
         */
        val OFFLINE_NETWORKING_INTENSITY:Int = 5
    }

    override fun continueCondition(time: DateTime): Boolean = time.year <= 1

    override fun createAgents(): List<IAgent> {
        // TODO: Test this
        return listOf(RealEstateAgent())
    }

    override fun createSensors(): List<ISensor> {
        // TODO: Implement this
        // TODO: Test this
        return emptyList()
    }
}