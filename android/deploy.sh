#!/bin/bash

export APK=Tribler-0.1-debug.apk

export ADB=/opt/android-sdk-linux/platform-tools/adb

if [ -z "$APK" ] || [ ! -f $APK ]; then
    echo ".apk not found"
    exit 1
fi

echo Uninstall app
$ADB uninstall org.tribler.android

echo Deploy $APK
$ADB install $APK

echo Launch app
$ADB shell monkey -p org.tribler.android -c android.intent.category.LAUNCHER 1
#$ADB shell am start -n org.tribler.android/org.kivy.android.PythonActivity

echo Wait for app to load
sleep 15

echo Make screenshot
$ADB shell screencap -p | sed 's/\r$//' > screen.png
