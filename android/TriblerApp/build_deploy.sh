#!/bin/bash

set -e

export ADB=/opt/android-sdk/platform-tools/adb

echo Install debug build
./gradlew installDebug

echo Start app
$ADB shell am start -n org.tribler.android/.MainActivity