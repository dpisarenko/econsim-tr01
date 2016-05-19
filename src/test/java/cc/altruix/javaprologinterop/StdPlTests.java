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

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.Prolog;
import alice.tuprolog.lib.InvalidObjectIdException;
import org.fest.assertions.Assertions;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by pisarenko on 10.02.2016.
 */
public final class StdPlTests {
    @Test
    public void containsOnlyDefaultScenario() throws InvalidObjectIdException, MalformedGoalException,
            IOException, InvalidTheoryException {
        final Prolog pl = PlUtils.createEngine();
        PlUtils.loadPrologFiles(pl, new String[] {"src/main/resources/std.pl"});
        containsOnlyTestLogic(pl, "abc", "abcdefg", true);
        containsOnlyTestLogic(pl, "abcdefg", "abc", false);
    }

    private void containsOnlyTestLogic(final Prolog pl, final String textToCheck,
                                       final String allowedChars, final boolean expectedResult)
            throws MalformedGoalException {
        ;
        Assertions.assertThat(
                pl.solve(
                        String.format(
                                "containsOnly('%s', '%s').",
                                textToCheck,
                                allowedChars
                        )
                ).isSuccess())
                .isEqualTo(expectedResult);
    }
}
