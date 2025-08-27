@echo off
echo "(step 1) - maven build"
CALL mvn clean install
echo "(step 2) - file transfer"
CALL scp ./target/raspberry-led.jar antoine@raspberrypi:/home/antoine/Documents/raspberry-led.jar
echo "(step 3) - running remotely"
CALL ssh antoine@raspberrypi "java -jar /home/antoine/Documents/raspberry-led.jar"