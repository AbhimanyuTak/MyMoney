#!/bin/bash

mvn clean install -DskipTests assembly:single -q
# mvn clean test
# mvn jacoco:report

# echo "INPUT 1"
# java -jar target/geektrust.jar sample_input/input1.txt
# echo "INPUT 2"
# java -jar target/geektrust.jar sample_input/input2.txt
# echo "INPUT 3"
# java -jar target/geektrust.jar sample_input/input3.txt
# echo "INPUT 4"
# java -jar target/geektrust.jar sample_input/input4.txt
# echo "INPUT 5"
java -jar target/geektrust.jar sample_input/input5.txt
# echo "INPUT 6"
# java -jar target/geektrust.jar sample_input/input6.txt