package cc.altruix.econsimtr01

/**
 * Created by pisarenko on 04.04.2016.
 */
abstract class DefaultSimulation(val timing : ITiming) : ISimulation {
    override fun run():SimResults {
        val results = SimResults()
        val agents = createAgents()
        val sensors = createSensors()
        while (timing.gotFuture() && continueCondition()) {
            val time = t0().plus(timing.tick()*1000L)
            agents.forEach { x -> x.act(time) }
            sensors.forEach { x -> x.measure(time) }
        }
        sensors.forEach { x -> x.finito() }
        return results

    }

    protected abstract  fun continueCondition(): Boolean
    protected abstract fun createAgents(): List<IAgent>
    protected abstract fun createSensors(): List<ISensor>
}