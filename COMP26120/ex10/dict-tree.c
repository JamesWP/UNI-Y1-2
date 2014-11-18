#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#define __USE_BSD
#include <string.h>

#include "speller.h"
#include "dict.h"

typedef struct node * tree_ptr;
struct node {
  Key_Type element; // only data is the key itself
  tree_ptr left, right;
  // add anything else that you need
};

struct table {
  tree_ptr head; // points to the head of the tree
  // add anything else that you need
};

Table initialize_table(/*ignore parameter*/) {
}

Table insert(Key_Type,Table) {
}

Boolean find(Key_Type, Table) {
}

void print_table(Table) {
}

void print_stats (Table) {
}
