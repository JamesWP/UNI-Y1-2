#!/bin/bash

make clean
make pangolins
(
  echo "yes"
) | pangolins > /dev/null

(
  echo "no"
  echo "pizza"
  echo "is it round"
  echo "yes"
) | pangolins > /dev/null

(
  echo "yes"
  echo "yes"
  echo "no"
) | pangolins > /dev/null

(
  echo "yes"
  echo "no"
  echo "cheese"
  echo "do you cook it"
  echo "no"
) | pangolins > /dev/null

(
  echo "no"
  echo "no"
  echo "the world"
  echo "is it bigger than a city"
  echo "yes"
) | pangolins > /dev/null

diff - pangolins.dat <<CORRECT
question: is it round
question: do you cook it
object: pizza
object: cheese
question: is it bigger than a city
object: the world
object: pangolin
CORRECT
