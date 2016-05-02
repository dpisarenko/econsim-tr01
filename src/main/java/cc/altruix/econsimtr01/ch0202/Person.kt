package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.generateId
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Person : DefaultAgent("person-${generateId()}") {
    val interactions = LinkedList<IInteractionWithProtagonist>()
}
