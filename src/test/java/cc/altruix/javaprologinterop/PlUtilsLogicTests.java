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
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Prolog;
import alice.tuprolog.UnknownVarException;
import alice.tuprolog.lib.InvalidObjectIdException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by pisarenko on 22.02.2016.
 */
public class PlUtilsLogicTests {
    @Test
    public void extractSingleStringDoesntThrowExceptionWhenNoResultsAreThere() throws
            UnknownVarException, NoMoreSolutionException,
            MalformedGoalException, NoSolutionException {
        final Prolog engine = Mockito.mock(Prolog.class);
        final String url = "url";
        final String predicate = "predicate";
        final PlUtilsLogic out = Mockito.spy(new PlUtilsLogic());
        Mockito.doReturn(Collections.<String>emptyList())
                .when(out).getResults(engine, "predicate('url', X).", "X");
        // Run method under test
        final String result =
                out.extractSingleString(engine, url, predicate);
        // Verify
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEmpty();
    }
    @Test
    public void extractSingleIntDoesntThrowExceptionWhenNoResultsAreThere() throws UnknownVarException,
            NoMoreSolutionException, MalformedGoalException, NoSolutionException {
        final Prolog engine = Mockito.mock(Prolog.class);
        final PlUtilsLogic out = Mockito.spy(new PlUtilsLogic());
        final String query = "predicate('url', X).";
        final String var = "X";
        Mockito.doReturn(Collections.<String>emptyList())
                .when(out).getResults(engine, query, var);
        // Run method under test
        final Integer result =
                out.extractSingleInt(engine, query, var);
        // Verify
        Assertions.assertThat(result).isNull();
    }
    @Test
    public void getResultsAsSetUsesGetFillResults()
            throws UnknownVarException, NoMoreSolutionException,
            MalformedGoalException, NoSolutionException {
        final PlUtilsLogic out = Mockito.spy(new PlUtilsLogic());
        final Prolog engine = Mockito.mock(Prolog.class);
        final String query = "query";
        final String varName = "varName";
        final Set set = Mockito.mock(Set.class);
        Mockito.doReturn(set).when(out).createSet();
        Mockito.doNothing().when(out).fillResults(engine, query, varName, set);
        // Run method under test
        out.getResultsAsSet(engine, query, varName);
        // Verify
        Mockito.verify(out).fillResults(engine, query, varName, set);
        Mockito.verify(out).createSet();
    }
    @Test
    public void loadPrologFilesCorrectlyHandlesEscapedSingleQuotes()
            throws InvalidObjectIdException, IOException, InvalidTheoryException,
            UnknownVarException, NoMoreSolutionException, MalformedGoalException,
            NoSolutionException {
        final PlUtilsLogic out = new PlUtilsLogic();
        final Prolog engine = PlUtils.createEngine();
        out.loadPrologFiles(engine,
                new String[] {
                        "src/test/resources/PlUtilsLogicTests.loadPrologFilesCorrectlyHandlesEscapedSingleQuotes.pl"
                }
        );
        final List<String> results = PlUtils.getResults(engine,
                String.format(
                        "worldbuildingTodo('%s', X, _).",
                        "9: Part 1: The Pilot / Chapter 2"),
                "X");
        Assertions.assertThat(results).isNotNull();
        Assertions.assertThat(results.size()).isEqualTo(1);
        Assertions.assertThat(
                PlUtils.removeSingleQuotes(results.get(0)))
                .isEqualTo(
                        "Describe pilot's room in Valkyrie's castle"
                );
    }
}
