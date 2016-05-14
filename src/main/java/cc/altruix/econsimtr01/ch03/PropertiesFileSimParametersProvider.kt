package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.IAgent
import cc.altruix.econsimtr01.ISimParametersProvider
import cc.altruix.econsimtr01.PlFlow
import cc.altruix.econsimtr01.PlTransformation
import cc.altruix.econsimtr01.ch0201.InfiniteResourceSupply
import cc.altruix.econsimtr01.ch0201.InitialResourceLevel
import java.io.File
import java.util.*

/**
 * Created by pisarenko on 14.05.2016.
 */
abstract open class PropertiesFileSimParametersProvider(val file: File) : ISimParametersProvider {
    override val agents:MutableList<IAgent> = LinkedList()
        get
    override val flows:MutableList<PlFlow> = LinkedList()
        get

    override val initialResourceLevels:MutableList<InitialResourceLevel> = LinkedList()
        get
    override val infiniteResourceSupplies:MutableList<InfiniteResourceSupply> = LinkedList()
        get
    override val transformations:MutableList<PlTransformation> = LinkedList()
        get
    lateinit var validity:ValidationResult
        get

    open fun initAndValidate() {
        validity = init(file)
    }
    abstract fun init(file: File):ValidationResult
}