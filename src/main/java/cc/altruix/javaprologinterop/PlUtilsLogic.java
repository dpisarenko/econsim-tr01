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
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Term;
import alice.tuprolog.Theory;
import alice.tuprolog.UnknownVarException;
import com.google.common.primitives.Ints;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pisarenko on 22.02.2016.
 */
public class PlUtilsLogic {
    private final static Logger LOGGER =
            LoggerFactory.getLogger(PlUtilsLogic.class);

    public String extractSingleString(final Prolog engine,
                                      final String firstArgumentValue,
                                      final String predicate) throws
            UnknownVarException, NoMoreSolutionException,
            MalformedGoalException,
            NoSolutionException {
        final List<String> result = getResults(
                engine,
                String.format(
                        "%s('%s', X).",
                        predicate,
                        firstArgumentValue
                ),
                "X"
        );
        if (result.size() > 0) {
            return result.get(0);
        }
        return "";
    }
    public Integer extractSingleInt(final Prolog engine,
                                    final String query,
                                    final String var) throws
            UnknownVarException, NoMoreSolutionException, MalformedGoalException,
            NoSolutionException {
        final List<String> result = getResults(engine, query, var);
        if (result.size() > 0) {
            return Ints.tryParse(result.get(0));
        } else {
            return null;
        }
    }
    public String extractSingleStringFromQuery(final Prolog engine,
                                               final String query,
                                               final String var) throws
            UnknownVarException, NoMoreSolutionException, MalformedGoalException,
            NoSolutionException {
        final List<String> result = getResults(engine, query, var);
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public List<String> getResults(final Prolog engine, final String query, final String varName) throws
            MalformedGoalException, NoSolutionException, UnknownVarException, NoMoreSolutionException {
        final LinkedList<String> result = new LinkedList<String>();
        fillResults(engine, query, varName, result);
        return result;
    }

    public List<Map<String,String>> getResults(final Prolog engine, final String query, final String[] varNames) throws MalformedGoalException, NoMoreSolutionException {
        final List<Map<String,String>> result = new LinkedList<>();

        try {

            SolveInfo res2 = engine.solve(query);
            if (res2.isSuccess()) {
                Map<String, String> row = addRow(result);
                addVariableValues(query, varNames, row, res2);

                while (engine.hasOpenAlternatives()) {
                    row = addRow(result);
                    res2 = engine.solveNext();
                    addVariableValues(query, varNames, row, res2);
                }
            }
        }
        catch (final MalformedGoalException|NoMoreSolutionException exception) {
            printError(query, varNames, exception);
            throw exception;
        }
        return result;
    }

    protected Map<String, String> addRow(List<Map<String, String>> result) {
        final Map<String,String> row = new HashMap<>();
        result.add(row);
        return row;
    }

    protected void addVariableValues(String query, String[] varNames, Map<String, String> row, SolveInfo res2l) {
        Arrays.stream(varNames).forEach(varName -> {
            try {
                row.put(varName, res2l.getTerm(varName).toString());
            } catch (NoSolutionException |UnknownVarException exception) {
                printError(query, varNames, exception);
            }
        });
    }

    protected void printError(final String query, final String[] varNames, final Exception exception) {
        final String varNameTxt = composeVarNameTxt(varNames);
        LOGGER.error(
                String.format(
                        "query='%s', varNames=%s",
                        query,
                        varNameTxt),
                exception
        );
    }

    protected String composeVarNameTxt(String[] varNames) {
        final StringJoiner stringJoiner = new StringJoiner(",");
        Arrays.stream(varNames).forEach(x -> stringJoiner.add(x));
        return stringJoiner.toString();
    }

    public Set<String> getResultsAsSet(final Prolog engine, final String query, final String varName) throws
            MalformedGoalException, NoSolutionException, UnknownVarException, NoMoreSolutionException {
        final Set<String> result = createSet();
        fillResults(engine, query, varName, result);
        return result;
    }

    protected Set<String> createSet() {
        return new HashSet<String>();
    }

    protected void fillResults(final Prolog engine,
                               final String query,
                               final String varName,
                               final Collection<String> result)
            throws MalformedGoalException,
            NoSolutionException, UnknownVarException, NoMoreSolutionException {
        try {
            SolveInfo res2 = engine.solve(query);
            if (res2.isSuccess()) {
                result.add(res2.getTerm(varName).toString());
                while (engine.hasOpenAlternatives()) {
                    res2 = engine.solveNext();
                    final Term x2 = res2.getTerm(varName);
                    result.add(x2.toString());
                }
            }
        }
        catch (final MalformedGoalException exception) {
            LOGGER.error(
                    String.format(
                            "query='%s', varName='%s'",
                            query,
                            varName),
                    exception
            );
            throw exception;
        }
    }

    public Integer countOccurrences(final Prolog engine, final String query)
            throws MalformedGoalException, NoMoreSolutionException {
        int resultsCount = 0;
        try {
            SolveInfo res = engine.solve(query);
            if (res.isSuccess()) {
                resultsCount++;
                while (engine.hasOpenAlternatives()) {
                    resultsCount++;
                    engine.solveNext();
                }
            }
        } catch (final MalformedGoalException exception) {
            LOGGER.error(
                    String.format(
                            "query='%s'",
                            query),
                    exception
            );
            throw exception;
        }
        return resultsCount;
    }
    public void loadPrologFiles(final Prolog engine,
                                final String[] files) throws IOException, InvalidTheoryException {
        final List<String> paths = Arrays.asList(files);
        final StringBuilder theoryBuilder = new StringBuilder();

        for (final String path : paths) {
            theoryBuilder.append(System.lineSeparator());
            theoryBuilder.append("% ");
            theoryBuilder.append(path);
            theoryBuilder.append(" (START)");
            theoryBuilder.append(System.lineSeparator());
            theoryBuilder.append(FileUtils.readFileToString(new File(path)));
            theoryBuilder.append(System.lineSeparator());
            theoryBuilder.append("% ");
            theoryBuilder.append(path);
            theoryBuilder.append(" (END)");
            theoryBuilder.append(System.lineSeparator());
        }

        final Theory test1 = new Theory(theoryBuilder.toString());
        engine.setTheory(test1);
    }
    public void loadPrologTheoryAsText(final Prolog engine, final String theoryTxt)
            throws InvalidTheoryException {
        engine.setTheory(new Theory(theoryTxt));
    }
}
