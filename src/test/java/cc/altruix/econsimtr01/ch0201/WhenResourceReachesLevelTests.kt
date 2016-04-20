package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.fest.assertions.Assertions
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by pisarenko on 20.04.2016.
 */
class WhenResourceReachesLevelTests {
    @Test
    fun connectToInitiatingAgent() {
        val out = WhenResourceReachesLevel("agent", "resource", 1.0)
        val agent = mock<DefaultAgent>()
        Mockito.`when`(agent.id()).thenReturn("agent")
        // Run method under test
        out.connectToInitiatingAgent(listOf(agent))
        // Verify
        Mockito.verify(agent).addResourceLevelObserver(out)
    }
    @Test
    fun possibleResourceLevelChangeUpdatesNextFireTimeWhenResourceAmountRight() {
        possibleResourceLevelChangeTestLogic(1.0, true)
        possibleResourceLevelChangeTestLogic(1.5, true)
    }

    private fun possibleResourceLevelChangeTestLogic(curAmount: Double, nextFireTimeUpdated: Boolean) {
        val out = WhenResourceReachesLevel("agent", "resource", 1.0)
        val agent = mock<DefaultAgent>()
        Mockito.`when`(agent.amount("resource")).thenReturn(curAmount)
        val t = 0L.millisToSimulationDateTime()
        out.nextFireTime = null
        // Run method under test
        out.possibleResourceLevelChange(agent, t)
        // Verify
        Assertions.assertThat(out.nextFireTime != null).isEqualTo(nextFireTimeUpdated)
        out.nextFireTime?.equals(t.plusMinutes(1))?.shouldBe(nextFireTimeUpdated)
    }

    @Test
    fun possibleResourceLevelChangeDoesNotUpdateNextFireTimeWhenResourceAmountWrong() {
        possibleResourceLevelChangeTestLogic(0.5, false)
    }
}