/*
 * Copyright 2012-2016 Dmitri Pisarenko
 *
 * WWW: http://altruix.cc
 * E-Mail: dp@altruix.co
 * Skype: dp118m (voice calls must be scheduled in advance)
 * Physical address:
 *
 * 4-i Rostovskii pereulok 2/1/20
 * 119121 Moscow
 * Russian Federation
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