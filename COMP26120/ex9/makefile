CC=gcc
CFLAGS=-g -std=c99 -Wall


clean:
		rm -f pangolins pangolins.dat >/dev/null 

test:
		./test.sh

pangolins:
		$(CC) $(CFLAGS) pangolins.c -o pangolins
