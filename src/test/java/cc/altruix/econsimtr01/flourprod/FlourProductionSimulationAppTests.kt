/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 *
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
 *
 * This file is part of econsim-tr01.
 *
 * econsim-tr01 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * econsim-tr01 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with econsim-tr01.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package cc.altruix.econsimtr01.flourprod

import cc.altruix.econsimtr01.assertFilesEqual
import cc.altruix.econsimtr01.mockTimeProvider
import org.junit.Test
import java.io.File

/**
 * @author Dmitri Pisarenko (dp@altruix.co)
 * @version $Id$
 * @since 1.0
 */
class FlourProductionSimulationAppTests {
    // TODO: Write tests for FlourProductionSimulationApp
    @Test
    fun integrationTest() {
        val actualFileName=
            "src/test/resources/flourprod/flourprod-1463541960000.csv"
        val actualFile = File(actualFileName)
        if (actualFile.exists()) {
            actualFile.delete()
        }
        val timeProvider = mockTimeProvider()
        val out = FlourProductionSimulationApp(
            timeProvider = timeProvider,
            targetDir =
            "src/test/resources/flourprod/"
        )
        out.run(arrayOf("src/test/resources/flourprod/"+
            "flourprodRye.properties",
            "src/test/resources/flourprod/"+
                "flourprodWheat.properties"),
            System.out,
            System.err)
        assertFilesEqual(actualFile,
            File("src/test/resources/flourprod/flourprod-1463541960000" +
                ".expected.csv"))
    }

}
