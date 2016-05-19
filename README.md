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


How to install TuProlog in Maven repository
-------------------------------------------
This project requires
[TuProlog](http://apice.unibo.it/xwiki/bin/view/Tuprolog/).
In order to install it into your local Maven repository, please do the
following steps:

 1. Download the file `2p-3.0.1.zip` from [TuProlog repository](https://bitbucket.org/tuprologteam/tuprolog/downloads).
 1. Unpack into directory `TUPROLOG`.
 1. Go to `TUPROLOG\2p-3.0.1\bin`.
 1. There, run following commands:
   1) `mvn install:install-file -Dfile=2p.jar -DgroupId=tuprolog
   -DartifactId=tuprolog-2p -Dversion=3.0.1 -Dpackaging=jar`
   1) `mvn install:install-file -Dfile=tuprolog.jar -DgroupId=tuprolog
 -DartifactId=tuprolog -Dversion=3.0.1 -Dpackaging=jar`

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