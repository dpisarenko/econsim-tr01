package cc.altruix.econsimtr01

import cc.altruix.econsimtr01.ch0201.After
import org.joda.time.DateTime
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by pisarenko on 14.04.2016.
 */
class PlFlowTests {
    @Test
    fun addFlowFiresUpFollowingTriggers() {
        val out = PlFlow("id","src", "target", "resource", null,
                { x -> true })
        val after1 = Mockito.spy(After("f1"))
        val after2 = Mockito.spy(After("f2"))

        out.addFollowUpFlow(after1)
        out.addFollowUpFlow(after2)

        val time = mock<DateTime>()

        // Run method under test
        out.addFlow(mock<IAgent>(), mock<IAgent>(), time)

        // Verify
        Mockito.verify(after1, Mockito.times(1)).updateNextFiringTime(time)
        Mockito.verify(after2, Mockito.times(1)).updateNextFiringTime(time)
    }

}