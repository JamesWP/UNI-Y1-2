#!/bin/bash
rm results
echo 1 >>results
(time part2 1 data10m) 2>>results 
echo 10 >>results
(time part2 10 data10m) 2>>results 
echo 100 >>results
(time part2 100 data10m) 2>>results 
echo 1000 >>results
(time part2 1000 data10m) 2>>results 
echo 10000 >>results
(time part2 10000 data10m) 2>>results 
echo 100000 >>results
(time part2 100000 data10m) 2>>results 
echo 1000000 >>results
(time part2 1000000 data10m) 2>>results
echo 10000000 >>results
(time part2 10000000 data10m) 2>> results 
