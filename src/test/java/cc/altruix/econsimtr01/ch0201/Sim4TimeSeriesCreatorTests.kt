package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.shouldBe
import org.junit.Test

/**
 * Created by pisarenko on 21.04.2016.
 */
class Sim4TimeSeriesCreatorTests {
    @Test
    fun initCreatesAllColumns() {
        val out = Sim4TimeSeriesCreator()
        out.columns.size.shouldBe(7)
        val titles =
        out.columns.map { "${it.title}" }
                .fold("", {x, y -> "$x$y,"})
        titles.shouldBe("t [min],Time,Money in savings account,Subscribers in the list (1 interaction),Software completion [hours spent],Software completion [%],Ready for transition? [yes/no],")
        val classes = out.columns.map { "${it.func.javaClass.name}" }
                .fold("", {x, y -> "$x$y,"})
        classes.shouldBe("cc.altruix.econsimtr01.ch0201.TimeSecondsColFunction,cc.altruix.econsimtr01.ch0201.TimeLongFormColFunction,cc.altruix.econsimtr01.ch0201.MoneyInSavingsAccountColFunction,cc.altruix.econsimtr01.ch0201.SubscribersCohortColFunction,cc.altruix.econsimtr01.ch0201.AbsoluteResourceLevelColFunction,cc.altruix.econsimtr01.ch0201.PercentageResourceLevelColFunction,cc.altruix.econsimtr01.ch0201.TransitionReadinessColFunction,")
    }
}