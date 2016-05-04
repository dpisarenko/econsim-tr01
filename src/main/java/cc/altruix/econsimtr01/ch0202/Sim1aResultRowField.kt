package cc.altruix.econsimtr01.ch0202

/**
 * Created by pisarenko on 04.05.2016.
 */
enum class Sim1aResultRowField(val description:String) {
    PEOPLE_WILLING_TO_MEET("Number of people willing to meet"),
    PEOPLE_WILLING_TO_RECOMMEND("Number of people willing to recommend my friend"),
    PEOPLE_MET("Number of people my friend had an offline networking session with"),
    PEOPLE_WILLING_TO_PURCHASE("Number of people willing to purchase my friend's services, if need arises")
}