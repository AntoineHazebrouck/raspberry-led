# Remote Raspberry pi

## Run remotely

Make sure that the raspberry is connected to your current network under the dns "raspberrypi"

```shell
./remote_run.sh
```

## SSH to Raspberry pi

```shell
ssh antoine@raspberrypi
```

Config to avoid password

```shell
ssh-keygen
ssh-copy-id antoine@raspberrypi
```