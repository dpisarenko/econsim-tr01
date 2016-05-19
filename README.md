Basic agriculture model
=======================
What is it?
-----------
Implementation of a basic model of growing wheat and rye. The description
of the underlying model can be found [here](altruix.cc/information-centric-model-of-economy/basic-model-of-agriculture/).

How to build
------------
`mvn package`

How to run
----------
`java -cp econsim-tr01-0.1-jar-with-dependencies.jar cc.altruix.econsimtr01.ch03.BasicAgriculturalSimulationAppKt`

How to create a distribution
----------------------------
1. Create a directory `dist` by copying `dist-template`
1. Build the JAR file (`mvn package`) and put into `dist`.
1. Copy `target/econsim-tr01-1.0-SNAPSHOT-jar-with-dependencies.jar` to `dist`
1. Test that it works.

License
-------
This software is distributed under the terms of the [General Public License](http://www.gnu.org/licenses/quick-guide-gplv3.html).
See file `COPYING` for details.