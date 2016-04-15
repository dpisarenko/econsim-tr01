package cc.altruix.econsimtr01

import org.junit.Test
import org.mockito.Mockito

/**
 * Created by pisarenko on 15.04.2016.
 */
class DefaultAgentTests {
    @Test
    fun actNotifiesSubscribers() {
        val action1 = mock<IAction>()
        Mockito.`when`(action1.timeToRun(Mockito.any())).thenReturn(true)
        val action2 = mock<IAction>()
        Mockito.`when`(action1.timeToRun(Mockito.any())).thenReturn(false)
        val out = DefaultAgent("agent1")
        out.actions.add(action1)
        out.actions.add(action2)
        val t = 0L.millisToSimulationDateTime()
        // Run method under test
        out.act(t)
        // Verify
        Mockito.verify(action1).notifySubscribers(t)
        Mockito.verify(action2, Mockito.never()).notifySubscribers(t)
    }
}