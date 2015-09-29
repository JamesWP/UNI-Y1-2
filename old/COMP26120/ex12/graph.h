
#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>
#define __USE_BSD // make prototype for strdup visible
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
} Graph;

// use to check result of strdup, malloc etc.
extern void check (void *memory, char *message);

extern int initialize_graph (Graph *mygraph, int MaxSize);
extern int insert_graph_node (Graph *mygraph, int n, char *name);
extern int insert_graph_link (Graph *mygraph, int source, int target);
extern int read_graph (Graph *mygraph, char *filename);
extern void print_graph (Graph *mygraph);

typedef struct queue{
  int max;
  int head;
  int tail;
  int items;
  Node** list;
}Queue;

void init_queue(Queue* q,int max);
bool has_items(Queue* q);
Node* pop_head(Queue* q);
bool item_in_queue(Queue* q,Node* item);
void append_item(Queue* q,Node* item);

extern void bfs(Graph* g,Node* start, void function(Node*));
extern void dfs(Graph* g,Node* start, void function(Node*));
