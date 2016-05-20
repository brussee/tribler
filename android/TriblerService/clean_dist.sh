#!/bin/bash

echo Clean dist

rm -rf dist/TriblerService/*
rm -rf /home/paul/.local/share/python-for-android/build/bootstrap_builds/*

p4a clean_dists
p4a clean_bootstraps

