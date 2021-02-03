(echo Stopping any running daemons:>output.txt
gradlew --stop>>output.txt
cls
echo Cleaning the gradle cache:>>output.txt
gradlew clean>>output.txt
cls
echo Generating all of the run files:>>output.txt
gradlew genEclipseRuns --refresh-dependencies>>output.txt
cls
echo Generating Eclipse Project files:>>output.txt
gradlew eclipse>>output.txt
cls)