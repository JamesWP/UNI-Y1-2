CC=gcc
CFLAGS=-g -std=c99 -Wall
#LDFLAGS=-lm

test3:
	./part3 #no parameters
	./part3 "only one parameter"
	./part3 "biggest parameter" "at" "start"
	./part3 "biggest" "parameter" "at" "end" "very very very big parameter"
	./part3 "answer" "somewhere" "in" "the" "middle"
	./part3 "two" "strings" "the" "same" "length" "ha ha !"


test4:
	./part4 -f 10
	./part4 -f 0
	./part4 -c 0
	./part4 ; /usr/bin/test "$$?" -eq 1 #test for error
	./part4 -f -10


test5:
	./part5 part5.c
	./part5 testinput 
