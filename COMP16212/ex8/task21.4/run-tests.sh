#!/bin/bash
set -e
javac *.java

java DuplicateVoters
java DuplicateVoters voting.txt
java DuplicateVoters voting2.txt
java DuplicateVoters voting.txt duplicates.txt

rm duplicates.txt