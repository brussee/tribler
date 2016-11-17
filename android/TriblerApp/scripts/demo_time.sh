#!/bin/bash

#$ADB logcat -c

#timestamp=$(date +%s)

echo 1
. ./phone1.sh
$ADB shell date
date

echo 2
. ./phone2.sh
$ADB shell date
date

echo 3
. ./phone3.sh
$ADB shell date
date

echo 4
. ./phone4.sh
$ADB shell date
date

echo 5
. ./phone5.sh
$ADB shell date
date

echo 6
. ./phone6.sh
$ADB shell date
date

echo 7
. ./phone7.sh
$ADB shell date
date

echo 8
. ./phone8.sh
$ADB shell date
date

echo 9
. ./phone9.sh
$ADB shell date
date

echo 10
. ./phone10.sh
$ADB shell date
date

echo 11
. ./phone11.sh
$ADB shell date
date

echo 12
. ./phone12.sh
$ADB shell date
date

echo 13
. ./phone13.sh
$ADB shell date
date

echo 14
. ./phone14.sh
$ADB shell date
date

