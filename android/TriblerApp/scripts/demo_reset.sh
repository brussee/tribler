#!/bin/bash

for VAR_i in 1 2 3 4 5 6 7 8 9 10 11 12 13 14
do
    echo $VAR_i
    . ./phone$VAR_i.sh
    $ADB shell date
    date
    #$ADB root
    $ADB logcat -c
    #./update.sh
done
