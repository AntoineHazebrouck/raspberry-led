# Remote Raspberry pi

## Run remotely

Make sure that the raspberry is connected to your current network under the dns "raspberrypi"

```bash
mvn clean install
scp ./target/raspberry-led.jar antoine@raspberrypi:/home/antoine/Documents/raspberry-led.jar
ssh antoine@raspberrypi "java -jar /home/antoine/Documents/raspberry-led.jar"
```

## SSH to Raspberry pi

```bash
ssh antoine@raspberrypi
```

Config to avoid password

```bash
ssh-keygen
ssh-copy-id antoine@raspberrypi
```