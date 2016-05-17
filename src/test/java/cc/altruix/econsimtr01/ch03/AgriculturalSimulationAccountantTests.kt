/*
 * Copyright (c) 2016 Dmitri Pisarenko, http://altruix.cc
 */

package cc.altruix.econsimtr01.ch03

import cc.altruix.econsimtr01.ch0202.SimResRow
import org.fest.assertions.Assertions
import org.joda.time.DateTime
import org.junit.Test
import java.util.*

/**
 * Created by pisarenko on 17.05.2016.
 */
class AgriculturalSimulationAccountantTests {
    @Test
    fun calculateFieldAreaWithCrop() {
        val simParamProv =
                AgriculturalSimParametersProviderWithPredefinedData(Properties())
        val field = Field(simParamProv)
        field.put(AgriculturalSimParametersProvider.RESOURCE_AREA_WITH_CROP.id,
                123.45)
        val agents = listOf(field)
        val out = createObjectUnderTest()
        Assertions.assertThat(out.calculateFieldAreaWithCrop(agents))
                .isEqualTo(123.45)
    }

    private fun createObjectUnderTest(): AgriculturalSimulationAccountant {
        val out = AgriculturalSimulationAccountant(HashMap<DateTime,
                SimResRow<AgriculturalSimulationRowField>>(), "scenario")
        return out
    }
}