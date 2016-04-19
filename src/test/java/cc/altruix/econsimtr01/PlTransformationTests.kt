package cc.altruix.econsimtr01

import org.joda.time.DateTime
import org.junit.Test

/**
 * Created by pisarenko on 19.04.2016.
 */
class PlTransformationTests {
    @Test
    fun timeToRunCallsTimeTriggerFunction() {
        val out = PlTransformation(
                "id",
                "agent",
                10.0,
                "res1",
                10.0,
                "res2",
                 {
            x -> (x.hourOfDay == 16) && (x.minuteOfHour == 45) && (x.secondOfMinute == 0)
        })
        out.timeToRun(DateTime(2016, 4, 19, 16, 45, 0)).shouldBeTrue()
        out.timeToRun(DateTime(2016, 4, 19, 16, 45, 0).plusMinutes(1)).shouldBeFalse()
        out.timeToRun(DateTime(2016, 4, 19, 16, 45, 0).minusMinutes(1)).shouldBeFalse()
    }
}