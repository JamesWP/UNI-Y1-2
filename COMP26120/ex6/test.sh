#!/bin/bash

(time part1 1 data1000) 2>>results 
(time part1 10 data1000) 2>>results 
(time part1 100 data1000) 2>>results 
(time part1 1000 data1000) 2>>results 
(time part1 10000 data10k) 2>>results 
(time part1 100000 data100k) 2>>results 
(time part1 1000000 data1M) 2>>results 
