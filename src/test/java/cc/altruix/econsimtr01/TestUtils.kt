package cc.altruix.econsimtr01

import org.fest.assertions.Assertions
import org.joda.time.DateTime

/**
 * Created by pisarenko on 09.04.2016.
 */
fun createDate(t0: DateTime,
                       days:Int,
                       hours:Int,
                       minutes:Int): DateTime{

    return t0.plusDays(days).plusHours(hours).plusMinutes(minutes)
}

fun Boolean.shouldBe(expectedValue:Boolean) = Assertions.assertThat(this).isEqualTo(expectedValue)
fun Boolean.shouldBeTrue() = shouldBe(true)
fun Boolean.shouldBeFalse() = shouldBe(false)

fun String.shouldBe(expectedValue:String) = Assertions.assertThat(this).isEqualTo(expectedValue)
fun Double.shouldBe(expectedValue:Double) = Assertions.assertThat(this).isEqualTo(expectedValue)
fun Double?.shouldBeNotNull() = Assertions.assertThat(this).isNotNull
