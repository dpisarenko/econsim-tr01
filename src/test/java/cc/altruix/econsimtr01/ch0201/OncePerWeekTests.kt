package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.*
import org.joda.time.DateTime
import org.junit.Test

/**
 * Created by pisarenko on 14.04.2016.
 */
class OncePerWeekTests {
    // TODO: Continue here, Once per week must fire every Monday, at Midnight
    @Test
    fun sunnyDay() {
        val daysOfWeek = arrayOf("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")

        val t0 = 0L.secondsToSimulationDateTime()

        for (i in 0..6) {
            val t = t0.plusDays(i)
            testLogic(daysOfWeek, t, daysOfWeek[i])
        }
    }

    private fun testLogic(daysOfWeek: Array<String>, time: DateTime, dayOfWeek: String) {
        time.toDayOfWeekName().shouldBe(dayOfWeek)

        OncePerWeek(dayOfWeek).invoke(time).shouldBeTrue()
        daysOfWeek.filter { !it.equals(dayOfWeek) }.forEach {
            OncePerWeek(it).invoke(time).shouldBeFalse()
        }
    }
}
