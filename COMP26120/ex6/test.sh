#!/bin/bash
rm results
echo 1 >>results
(time part1 1 data1000) 2>>results 
echo 10 >>results
(time part1 10 data1000) 2>>results 
echo 100 >>results
(time part1 100 data1000) 2>>results 
echo 1000 >>results
(time part1 1000 data1000) 2>>results 
echo 10000 >>results
(time part1 10000 data10k) 2>>results 
echo 100000 >>results
(time part1 100000 data100k) 2>>results 
echo 1000000 >>results
(time part1 1000000 data1M) 2>>results 
