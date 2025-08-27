echo "(step 1) - maven build"
mvn clean install
echo "(step 2) - file transfer"
scp ./target/raspberry-led.jar antoine@raspberrypi:/home/antoine/Documents/raspberry-led.jar
echo "(step 3) - running remotely"
ssh antoine@raspberrypi "java -jar /home/antoine/Documents/raspberry-led.jar"