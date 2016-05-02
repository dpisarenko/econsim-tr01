package cc.altruix.econsimtr01.ch0202

import org.fest.assertions.Assertions
import org.junit.Test

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class PersonTests {
    @Test
    fun willingToRecommendSetterWorks() {
        val out = Person()
        out.willingToRecommend = true
        Assertions.assertThat(out.willingToRecommend).isTrue()
    }
}
