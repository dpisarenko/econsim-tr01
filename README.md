Basic agriculture model
=======================
How to build
------------
`mvn package`

How to run
----------
`java -cp econsim-tr01-1.0-SNAPSHOT-jar-with-dependencies.jar cc.altruix.econsimtr01.ch03.BasicAgriculturalSimulationAppKt`

How to create a distribution
----------------------------
1. Create a directory `dist` by copying `dist-template`
1. Build the JAR file (`mvn package`) and put into `dist`.
1. Copy `target/econsim-tr01-1.0-SNAPSHOT-jar-with-dependencies.jar` to `dist`
1. Test that it works.