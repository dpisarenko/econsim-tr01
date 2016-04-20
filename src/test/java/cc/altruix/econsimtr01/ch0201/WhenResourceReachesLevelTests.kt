package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.mock
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

}