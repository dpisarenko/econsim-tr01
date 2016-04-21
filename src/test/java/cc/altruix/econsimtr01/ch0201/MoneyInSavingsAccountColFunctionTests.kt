package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.shouldBe
import cc.altruix.econsimtr01.toPrologTheory
import org.junit.Test

/**
 * Created by pisarenko on 21.04.2016.
 */
class MoneyInSavingsAccountColFunctionTests {
    @Test
    fun invoke() {
        val out = MoneyInSavingsAccountColFunction()
        out.invoke(
                "resourceLevel(123, savingsAccount, r2, 210.23).".toPrologTheory(),
                123L)
                .shouldBe("210.23")
    }
}