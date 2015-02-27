CC=gcc
CFLAGS=-Wall -pedantic -g -std=c99
LDFLAGS=-lm

all: enum dp bnb greedy


enum: enum.o knapsack-util.o
	$(CC) $(CFLAGS) enum.o knapsack-util.o -o enum $(LDFLAGS)

dp: dp.o knapsack-util.o
	$(CC) $(CFLAGS) dp.o knapsack-util.o -o dp $(LDFLAGS)

bnb: bnb.o knapsack-util.o
	$(CC) $(CFLAGS) bnb.o knapsack-util.o -o bnb $(LDFLAGS)

greedy: greedy.o knapsack-util.o
	$(CC) $(CFLAGS) greedy.o knapsack-util.o -o greedy $(LDFLAGS)

clean:
	rm -rf *o enum dp bnb greedy
