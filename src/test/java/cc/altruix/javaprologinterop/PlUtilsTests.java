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

import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Term;
import alice.tuprolog.UnknownVarException;
import alice.tuprolog.Var;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

/**
 * Created by pisarenko on 21.02.2016.
 */
public final class PlUtilsTests {
    @Test
    public void getResultsUsesPassedVariableName() throws UnknownVarException, NoMoreSolutionException,
            MalformedGoalException, NoSolutionException {
        final Prolog engine = Mockito.mock(Prolog.class);
        final String query = "hasRedditPoints(Url, _).";
        final String varName = "Url";

        final SolveInfo solveInfo1 = Mockito.mock(SolveInfo.class);

        Mockito.when(solveInfo1.isSuccess()).thenReturn(true);
        Mockito.when(solveInfo1.getTerm(varName)).thenReturn(createStringTerm("Url1"));
        Mockito.when(engine.hasOpenAlternatives()).thenReturn(true).thenReturn(false);

        final SolveInfo solveInfo2 = Mockito.mock(SolveInfo.class);
        Mockito.when(engine.solveNext()).thenReturn(solveInfo2);
        Mockito.when(solveInfo2.getTerm(varName)).thenReturn(createStringTerm("Url2"));
        Mockito.when(engine.solve(query)).thenReturn(solveInfo1).thenReturn(solveInfo2);
        // Run method under test
        final List<String> result = PlUtils.getResults(engine, query, varName);
        // Verify
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains("Url1");
        Assertions.assertThat(result).contains("Url2");
        Mockito.verify(solveInfo1).getTerm(varName);
        Mockito.verify(solveInfo2).getTerm(varName);
    }
    @Test
    public void extractSingleStringUsesSingleQuotes() throws UnknownVarException, NoMoreSolutionException,
            MalformedGoalException, NoSolutionException {
        final Prolog engine = Mockito.mock(Prolog.class);
        final SolveInfo solveInfo = Mockito.mock(SolveInfo.class);
        Mockito.when(solveInfo.isSuccess()).thenReturn(true);
        Mockito.when(solveInfo.getTerm(Mockito.anyString())).thenReturn(createStringTerm("X"));
        Mockito.when(engine.hasOpenAlternatives()).thenReturn(false);
        Mockito.when(engine.solve("writtenBy('dataSet01-000', X).")).thenReturn(solveInfo);
        // Run method under test
        PlUtils.extractSingleString(engine, "dataSet01-000", "writtenBy");
        // Verify
        Mockito.verify(engine).solve("writtenBy('dataSet01-000', X).");
    }

    private Term createStringTerm(final String value) {
        final Var var = new Var(value);
        return var;
    }
}
