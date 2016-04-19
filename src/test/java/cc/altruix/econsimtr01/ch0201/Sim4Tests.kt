package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.ResourceFlow
import cc.altruix.econsimtr01.simulationRunLogic
import org.junit.Test
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 19.04.2016.
 */
class Sim4Tests {
    @Test
    fun test() {
        // TODO: Add a resource "productive time".
        // TODO: Make sure that at the beginning of every week that productive time is set to 40 hours.
        // TODO: Flow, every week. 20 hours of time are transformed into imaginary software features. By working 20 hours per week, she adds X percen to software completion.

        // TODO: 1. Stacy has done this for a while and knows that if she invests 20 hours per week over 6 months, the software will be completed. In other words, it costs her 480 person hours (20 hours per week * 4 weeks per month * 6 months = 480) to develop the software.
        // TODO: 1. If Stacy gets rid of distractions, she can spend up to 40 hours per week on productive activities, which aren't related to her day job.
        // TODO: 1. Stacy spends 20 hours per week on the guest posting activities (incl. all preparatory steps) and publishes 1 guest post every two weeks.
        // TODO: 1. 1 guest post brings her, on average, 20 new subscribers.

        val flows = LinkedList<ResourceFlow>()
        val log = StringBuilder()
        val simParametersProvider = Sim4ParametersProvider(
                File("src/test/resources/ch0201Sim4Tests.params.pl").readText()
        )
        val sim = Sim4(
                log,
                flows,
                simParametersProvider
        )
        simulationRunLogic(sim,
                log,
                simParametersProvider.resources,
                flows,
                "src/test/resources/ch0201/sim04/Sim4Tests.test.pl.expected.txt",
                "src/test/resources/ch0201/sim04/Sim4Tests.test.csv.expected.txt",
                "src/test/resources/ch0201/sim04/Sim4Tests.test.flows.actual.png",
                Sim4TimeSeriesCreator()
        )
    }

}