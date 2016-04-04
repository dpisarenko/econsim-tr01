package cc.altruix.econsimtr01

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Farmer(foodStorage: IResourceStorage) : IAgent {
    val actions = listOf<IAction>(EatingAction())
    override fun act(time: Long) {
        actions
                .filter { x -> x.timeToRun(time) }
                .forEach { x -> x.run() }
    }
}
