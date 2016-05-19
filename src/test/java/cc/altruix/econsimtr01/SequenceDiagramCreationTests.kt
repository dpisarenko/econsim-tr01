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

package cc.altruix.econsimtr01

import net.sourceforge.plantuml.SourceStringReader
import org.junit.Test
import java.io.File

/**
 * Created by pisarenko on 05.04.2016.
 */
class SequenceDiagramCreationTests {
    @Test
    fun test() {
        val srcTxt = "@startuml\n" +
        "Alice -> Bob: Authentication Request\n" +
        "Bob --> Alice: Authentication Response\n" +
        "\n" +
        "Alice -> Bob: Another authentication Request\n" +
        "Alice <-- Bob: another authentication Response\n" +
        "@enduml\n"

        val reader = SourceStringReader(srcTxt)
        // Write the first image to "png"
        reader.generateImage(File("src/test/resources/SequenceDiagramCreationTests_test.png"))
        // Return a null string if no generation
    }
}