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
import alice.tuprolog.Library;
import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.NoMoreSolutionException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Struct;
import alice.tuprolog.UnknownVarException;
import alice.tuprolog.event.OutputEvent;
import alice.tuprolog.event.OutputListener;
import alice.tuprolog.lib.InvalidObjectIdException;
import alice.tuprolog.lib.OOLibrary;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by pisarenko on 10.02.2016.
 */
public class PlUtils {
    private final static PlUtilsLogic LOGIC = new PlUtilsLogic();
    private static final String SINGLE_QUOTE = "\'";

    private PlUtils() {
    }

    public static Prolog createEngine() throws InvalidObjectIdException {
        final Prolog engine = new Prolog();
        engine.addOutputListener(new OutputListener() {
            public void onOutput(OutputEvent outputEvent) {
                System.out.println(String.format("PROLOG: %s", outputEvent.getMsg()));
            }
        });
        final Library lib = engine.getLibrary("alice.tuprolog.lib.OOLibrary");
        ((OOLibrary) lib).register(new Struct("stdout"), System.out);
        return engine;
    }

    public static void loadPrologFiles(final Prolog engine,
                                       final String[] files) throws IOException, InvalidTheoryException {
        LOGIC.loadPrologFiles(engine, files);
    }

    public static String extractSingleStringFromQuery(final Prolog engine,
                                                      final String query,
                                                      final String var) throws UnknownVarException, NoMoreSolutionException,
            MalformedGoalException, NoSolutionException {
        return LOGIC.extractSingleStringFromQuery(engine, query, var);
    }

    public static List<String> getResults(final Prolog engine, final String query, final String varName) throws
            MalformedGoalException, NoSolutionException, UnknownVarException, NoMoreSolutionException {
        return LOGIC.getResults(engine, query, varName);
    }

    public static Integer extractSingleInt(final Prolog engine,
                                           final String query,
                                           final String var) throws
            UnknownVarException, NoMoreSolutionException, MalformedGoalException, NoSolutionException {
        return LOGIC.extractSingleInt(engine, query, var);
    }

    public static String extractSingleString(final Prolog engine,
                                             final String firstArgumentValue,
                                             final String predicate) throws
            UnknownVarException, NoMoreSolutionException, MalformedGoalException, NoSolutionException {
        return LOGIC.extractSingleString(engine, firstArgumentValue, predicate);
    }
    public static Set<String> getResultsAsSet(final Prolog engine, final String query, final String varName) throws
            UnknownVarException, NoMoreSolutionException, MalformedGoalException, NoSolutionException {
        return LOGIC.getResultsAsSet(engine, query, varName);
    }
    public static String removeSingleQuotes(final String txt) {
        if (txt.startsWith(SINGLE_QUOTE) && txt.endsWith(SINGLE_QUOTE)) {
            return txt.substring(1, txt.length()-1);
        }
        return txt;
    }

    public static Set<String> removeSingleQuotesFromSet(final Set<String> strings) throws UnknownVarException,
            NoMoreSolutionException, MalformedGoalException, NoSolutionException {
        return strings.stream()
                .map(urlWithQuotes -> removeSingleQuotes(urlWithQuotes))
                .collect(Collectors.toSet());
    }
    public static boolean predicateExists(final Prolog engine, final String predicate, final String value) throws MalformedGoalException {
        final SolveInfo res = engine.solve(String.format("%s('%s').", predicate, value));
        return res.isSuccess();
    }
    public static Integer countOccurrences(final Prolog engine,
                                           final String query) throws NoMoreSolutionException, MalformedGoalException {
        return LOGIC.countOccurrences(engine, query);
    }
    public static List<Map<String,String>> getResults(final Prolog engine, final String query, final String[] varNames)
            throws MalformedGoalException, NoMoreSolutionException {
        return LOGIC.getResults(engine, query, varNames);
    }
    public static void loadPrologTheoryAsText(final Prolog engine, final String theoryTxt)
            throws InvalidTheoryException {
        LOGIC.loadPrologTheoryAsText(engine, theoryTxt);
    }
}
