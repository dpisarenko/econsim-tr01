package cc.altruix.econsimtr01

import org.fest.assertions.Assertions
import org.junit.Test

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class UuidGeneratorTests {
    @Test
    fun createUuid() {
        Assertions.assertThat(UuidGenerator.createUuid()).isEqualTo("000001")
        Assertions.assertThat(UuidGenerator.createUuid()).isEqualTo("000002")
        Assertions.assertThat(UuidGenerator.createUuid()).isEqualTo("000003")
    }
}
