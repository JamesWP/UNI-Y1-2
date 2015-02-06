CFLAGS=-g -Wall -std=c99
CC=gcc

# "make tree" or "make hash" to compile your code

tree: speller.o dict-tree.o
	$(CC) $(LDFLAGS) -o $@ $^

hash: speller.o dict-hash.o
	$(CC) $(LDFLAGS) -o $@ $^

tidy:
	-rm *.o tree hash sorted_* shuffled_* labscript

################################################################################
# Comment out the next line once you have debugged your code
RUN=valgrind
# Uncomment the next line to time your code (instead of using valgrind)
#RUN=time
# If you comment out both RUNs above you can run your program by itself

# to test: "make testtree" or "make testhash" or "make tree1" or "make hash1"

MODE=-m 1

# small dictionaries that give trees that are unbalanced in various ways
testtree: tree
	$(RUN) ./$< -d LLdictionary $(MODE) -vvv sample-file-tree
	$(RUN) ./$< -d LRdictionary $(MODE) -vvv sample-file-tree
	$(RUN) ./$< -d RLdictionary $(MODE) -vvv sample-file-tree
	$(RUN) ./$< -d RRdictionary $(MODE) -vvv sample-file-tree
	@echo
	$(RUN) ./$< -d sample-dictionary $(MODE) -vv sample-file

testhash: hash
	$(RUN) ./$< -d sample-dictionary -s 13 $(MODE) -vv sample-file
	@echo "This should fail as the dictionary is bigger than the hashtable"
	$(RUN) ./$< -d sample-dictionary -s 5 $(MODE) -vvv sample-file

%1: %
	$(RUN) ./$< -d sample-dictionary -s 13 -vvv $(MODE) sample-file

###############################################################################
# uncomment one TEXT, DICT and SIZE then "make tree2" or "make hash2"

TEXT=labscript
#TEXT=any other text file you might like to use

labscript:
	@wget -O $@ http://www.cs.manchester.ac.uk/ugt/COMP26120/lab/ex10.html

# Dictionaries just made up of the words in TEXT, either sorted or in a
# random (shuffled) order, so every word will seem to be correctly spelt.
# (You can edit the dictionary to cause some "mis-spellings".)
#DICT=sorted_$(TEXT)
DICT=shuffled_$(TEXT)

sorted_$(TEXT): $(TEXT)
	@tr -sc A-Za-z '\012' <$< | tr A-Z a-z | sort -u | egrep -v "^$$">$@

shuffled_$(TEXT): sorted_$(TEXT)
	@shuf $< >$@

# A size of 1009 should be big enough for dictionaries with a few hundred
# different words in - check by "wc -l dictionary-name"
SIZE=1009

# To really test the speed of your algorithm, use a BIG dictionary:
#DICT=/usr/share/dict/words
#SIZE=1000003

%2: % $(DICT) $(TEXT)
	$(RUN) ./$< -d $(DICT) -s $(SIZE) $(MODE) $(TEXT)
