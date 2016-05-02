package cc.altruix.econsimtr01.ch0202

import cc.altruix.econsimtr01.DefaultAgent
import cc.altruix.econsimtr01.generateId

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class Person : DefaultAgent("person-${generateId()}") {
    var willingToMeet: Boolean = false
        get
        set
    var willingToRecommend: Boolean = false
        get
        set

}
