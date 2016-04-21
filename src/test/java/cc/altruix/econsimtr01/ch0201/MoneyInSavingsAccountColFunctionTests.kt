package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.shouldBe
import cc.altruix.econsimtr01.toPrologTheory
import org.junit.Test

/**
 * Created by pisarenko on 21.04.2016.
 */
class MoneyInSavingsAccountColFunctionTests {
    // TODO: 15:13:37.754 [main] ERROR c.a.e.ch0201.Sim1ParametersProvider - Can't find list agent
    // TODO: 15:13:37.757 [main] ERROR c.a.e.ch0201.Sim1ParametersProvider - Could not find list agent

    @Test
    fun invoke() {
        val out = MoneyInSavingsAccountColFunction()
        out.invoke(
                "resourceLevel(123, savingsAccount, r2, 210.23).".toPrologTheory(),
                123L)
                .shouldBe("210.23")
    }
}