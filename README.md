# Remote Raspberry pi

## Run remotely

Make sure that the raspberry is connected to your current network under the dns "raspberrypi"

```shell
./remote_run.bat
```

## SSH to Raspberry pi

```shell
ssh antoine@raspberrypi
```

Config to avoid password

```shell
ssh-keygen
# ssh-copy-id antoine@raspberrypi
type ~\.ssh\id_ed25519.pub | ssh antoine@raspberrypi "cat >> .ssh/authorized_keys"
```