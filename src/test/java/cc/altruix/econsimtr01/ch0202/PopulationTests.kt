package cc.altruix.econsimtr01.ch0202

import org.fest.assertions.Assertions
import org.junit.Test

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class PopulationTests {
    @Test
    fun initCreatesPeopleWillingToRecommend() {
        val initialNetworkSize = 10
        val out = Population(initialNetworkSize)
        Assertions.assertThat(out.people).isNotNull
        Assertions.assertThat(out.people.size).isEqualTo(initialNetworkSize)
        out.people.forEach {
            Assertions.assertThat(it.willingToRecommend).isTrue()
        }
    }
}
