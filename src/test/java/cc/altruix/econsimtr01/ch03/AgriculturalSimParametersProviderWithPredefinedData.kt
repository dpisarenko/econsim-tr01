package cc.altruix.econsimtr01.ch03

import java.io.File
import java.util.*

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class AgriculturalSimParametersProviderWithPredefinedData(val prop: Properties) :
        AgriculturalSimParametersProvider(File("someFile")) {
    override fun loadData():Properties = prop

    override fun initAndValidate() {
        super.initAndValidate()
    }
}
