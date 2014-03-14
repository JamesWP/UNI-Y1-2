#!/bin/bash
echo "compiling " *.java
javac *.java

echo "running"

for t in `seq 5`; do
  let "test = ( $RANDOM % 20 ) + 1"
  for i in `seq $test`; do
    echo "$RANDOM $i";
  done | sort | sed -E 's/^[0-9]+ //' > testdata
  echo "testing with test data:"
  cat testdata | tr '\n' ' '
  echo
  java MeanMinMaxMinusMean `cat testdata`
  rm testdata
done

for t in `seq 5`; do
  let "test = ( $RANDOM % 20 ) + 3"
  for i in `seq $test`; do
    echo "$RANDOM $i";
  done | sort | sed -E 's/^[0-9]+ //' | tail -n `let test-=2&&echo $test` > testdata
  echo "testing with test data with non consecutive nums:"
  cat testdata | tr '\n' ' '
  echo
  java MeanMinMaxMinusMean `cat testdata`
  rm testdata
done
