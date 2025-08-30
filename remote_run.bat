@echo off
echo "(step 1) - killing current remote program"
CALL ssh antoine@raspberrypi "sudo pkill java"
echo "(step 2) - removing former artifact"
CALL ssh antoine@raspberrypi "rm /home/antoine/Documents/raspberry-led.jar"
echo "(step 3) - maven build"
CALL mvn clean package -Dmaven.test.skip=true
echo "(step 4) - file transfer"
CALL scp ./target/raspberry-led.jar antoine@raspberrypi:/home/antoine/Documents/raspberry-led.jar
echo "(step 5) - running remotely"
CALL ssh antoine@raspberrypi "sudo java -jar /home/antoine/Documents/raspberry-led.jar"