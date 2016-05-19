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

package cc.altruix.javaprologinterop;

import alice.tuprolog.InvalidLibraryException;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;
import alice.tuprolog.UnknownVarException;
import alice.tuprolog.lib.InvalidObjectIdException;
import org.fest.assertions.Assertions;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by pisarenko on 22.01.2016.
 */
public final class Test1 {
    @Test
    public void test() throws InvalidTheoryException, InvalidObjectIdException,
            MalformedGoalException, IOException,
            UnknownVarException, NoSolutionException, NoMoreSolutionException {
        final Prolog engine = PlUtils.createEngine();

        final Theory test1 = new Theory(new FileInputStream
                ("src/test/resources/test1.pl"));
        engine.setTheory(test1);
        final List<String> result = PlUtils.getResults(engine, "participates('scene1', X).", "X");
        System.out.println("result: " + result);
    }

    @Test
    public void guestPostUniquenessViolationDetectionWorks() throws InvalidObjectIdException, IOException,
            MalformedGoalException, InvalidTheoryException, UnknownVarException, NoSolutionException,
            NoMoreSolutionException, InvalidLibraryException {

        guestPostUniquenessViolationDetectionTestLogic(new String[]{
                "src/test/resources/ontology.pl",
                "src/test/resources/invalidFile.pl"
        }, false);
        guestPostUniquenessViolationDetectionTestLogic(new String[]{
                "src/test/resources/ontology.pl",
                "src/test/resources/validFile.pl"
        }, true);
    }

    @Test
    public void allElementsDifferentSunnyDay() throws InvalidObjectIdException, IOException, InvalidTheoryException,
            MalformedGoalException {
        final Prolog engine = PlUtils.createEngine();
        PlUtils.loadPrologFiles(engine, new String[] {"src/test/resources/ontology.pl"});
        Assertions.assertThat(engine.solve("allElementsDifferent(['gp1', 'gp1']).").isSuccess()).isFalse();
        Assertions.assertThat(engine.solve("allElementsDifferent(['gp1', 'gp2']).").isSuccess()).isTrue();
    }

    private void guestPostUniquenessViolationDetectionTestLogic(String[] files, boolean expectedResult)
            throws InvalidObjectIdException, IOException, InvalidTheoryException, MalformedGoalException {
        final Prolog engine = PlUtils.createEngine();
        PlUtils.loadPrologFiles(engine, files);
        SolveInfo res2 = engine.solve("guest_post_ids_unique.");
        Assertions.assertThat(res2.isSuccess()).isEqualTo(expectedResult);
    }

}
