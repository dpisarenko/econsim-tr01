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

package cc.altruix.econsimtr01;

import net.sourceforge.plantuml.SourceStringReader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pisarenko on 05.04.2016.
 */
public final class App {
    public void run(final InputStream stream) {
        try
        {
            final String dirName = String.format(
                    "%s/econsim-tr01-%d",
                    System.getProperty("user.dir"),
                    new Date().getTime()
            );
            final File dir = new File(dirName);
            //noinspection ResultOfMethodCallIgnored
            dir.mkdir();

            final String theoryTxt = IOUtils.toString(stream);
            final SimParametersProvider simParametersProvider =
                    new SimParametersProvider(theoryTxt,
                            Collections.emptyList(),
                            Collections.emptyList(),
                            Collections.emptyList(),
                            Collections.emptyList());
            final List<ResourceFlow> flows = new LinkedList<>();
            final StringBuilder log = new StringBuilder();

            new Simulation1(log, flows, simParametersProvider).run();

            final File simResults = new File(String.format("%s/simResults.pl", dir.getAbsolutePath()));
            FileUtils.writeStringToFile(simResults, log.toString());
            createCsvFile(dir, simResults);
            createFlowsDiagram(dir, flows);
            createParamsFile(dir, theoryTxt);
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    private void createParamsFile(final File dir, String theoryTxt) throws IOException {
        final File paramsFile = new File(String.format("%s/simParams.pl", dir.getAbsolutePath()));
        FileUtils.writeStringToFile(paramsFile, theoryTxt);
    }

    private void createCsvFile(final File dir, File simResults) throws IOException {
        final String actualConvertedSimResults = new Simulation1TimeSeriesCreator().prologToCsv(simResults);
        final File simResultsCsvFile = new File(String.format("%s/simResults.csv", dir.getAbsolutePath()));
        FileUtils.writeStringToFile(simResultsCsvFile, actualConvertedSimResults);
    }

    private void createFlowsDiagram(final File dir, List<ResourceFlow> flows) throws IOException {
        final String flowString = new FlowDiagramTextCreator(Collections.emptyList()).createFlowDiagramText(flows);
        new SourceStringReader(flowString).generateImage(new File("%s/flows.png", dir.getAbsolutePath()));
    }

    public static void main(final String[] args) {
        new App().run(System.in);
    }
}
