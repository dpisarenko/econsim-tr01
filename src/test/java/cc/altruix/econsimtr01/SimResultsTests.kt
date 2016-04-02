package cc.altruix.econsimtr01

import org.fest.assertions.Assertions
import org.junit.Test

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class SimResultsTests {
    @Test
    fun dataIsNotNullAfterConstruction() {
        val map = SimResults().data
        Assertions.assertThat(map).isNotNull
        Assertions.assertThat(map).isEmpty()
    }
}
