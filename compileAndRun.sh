#!/usr/bin/env bash

if [[ -z $1 ]]; then
    echo "Please provide a class to run";
    exit 1;
fi
javac src/*.java -d out/
cd out
java $1
cd ..
