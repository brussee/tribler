#!/bin/bash

#export DEVICE=model:Nexus_10

#export ADB="adb -s $DEVICE"
export ADB=adb

echo Clean logcat
$ADB logcat -c

./uninstall.sh

./remove_appstate.sh

./install.sh
#./install_grant.sh

./default_appstate.sh
