package cc.altruix.econsimtr01.ch0201

import cc.altruix.econsimtr01.DefaultAgent

/**
 * Created by pisarenko on 14.04.2016.
 */
class ListAgent(id:String) : DefaultAgent(id) {
    companion object {
        val subscriberTypes = arrayOf(
                "r06-pc1",
                "r07-pc2",
                "r08-pc2",
                "r09-pc2",
                "r10-pc2",
                "r11-pc2",
                "r12-pc2"
        )
    }
    override fun put(res: String, amt: Double) {
        if (subscriberTypes.contains(res)) {
            // TODO: Test this
            addSubscribers(res, amt)
        } else {
            super.put(res, amt)
        }
    }

    fun addSubscribers(res: String, amt: Double) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}