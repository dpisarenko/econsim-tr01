package cc.altruix.econsimtr01.ch0201

import alice.tuprolog.Prolog
import cc.altruix.econsimtr01.*
import cc.altruix.javaprologinterop.PlUtils
import java.util.*

/**
 * Created by pisarenko on 08.04.2016.
 */
class Sim1ParametersProvider(val theoryTxt: String) {
    var resources:List<PlResource>
        get
        private set

    init {
        val prolog = theoryTxt.toPrologTheory()
        this.resources = extractResources(prolog)
    }

    private fun extractResources(prolog: Prolog): ArrayList<PlResource> {
        val resData = prolog.getResults("resource(Id, Name, Unit).",
                "Id", "Name", "Unit")
        val resList = ArrayList<PlResource>(resData.size)
        resData.forEach { map ->
            val res = PlResource(
                    map.get("Id").emptyIfNull().removeSingleQuotes(),
                    map.get("Name").emptyIfNull().removeSingleQuotes(),
                    map.get("Unit").emptyIfNull().removeSingleQuotes()
            )
            resList.add(res)
        }
        return resList
    }
}