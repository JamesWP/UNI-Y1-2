
#ifndef EX13_GRAPH
#define EX13_GRAPH


#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdbool.h>

typedef struct linkedlist { // linked list of ints (for use in Node)
  int index;
  struct linkedlist *next;
} List;

typedef struct { // a Node of a Graph
  char *name;
  List *outlist; // adjacency list
  int outdegree; // length of outlist
  int indegree; // number of nodes pointing here
  //double pagerank_score; //not needed for this exercise
} Node;

typedef struct {
  // your code goes here
  int MaxSize;
  Node** table;
  int maxID;
} Graph;

// use to check result of strdup, malloc etc.
extern void check (void *memory, char *message);

extern int initialize_graph (Graph *mygraph, int MaxSize);
extern int insert_graph_node (Graph *mygraph, int n, char *name);
extern int insert_graph_link (Graph *mygraph, int source, int target);
extern int read_graph (Graph *mygraph, char *filename);
extern void print_graph (Graph *mygraph);


#endif
