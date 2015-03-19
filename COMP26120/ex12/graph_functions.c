#include "graph.h"

void addToList(Node *node, int val){
  if(node->outlist==NULL){
    node->outlist = (List*)malloc(sizeof(List));
    node->outlist->index = val;
    node->outlist->next = NULL;
    return;
  }
  List* curent = node->outlist;
  while(curent->next!=NULL){
    curent = curent->next;
  }
  curent->next = (List*)malloc(sizeof(List));
  curent->next->index = val;
  curent->next->next = NULL;
}

int initialize_graph (Graph *mygraph, int MaxSize) {
  // your code goes here
  mygraph->MaxSize = MaxSize;
  mygraph->table = (Node**) malloc(sizeof(Node*)*MaxSize);
  return MaxSize;
}

int insert_graph_node (Graph *mygraph, int n, char *name) {
  mygraph->table[n] = (Node*) malloc(sizeof(Node));
  mygraph->table[n]->name = strdup(name);
  mygraph->table[n]->outdegree = 0;
  mygraph->table[n]->indegree = 0;
  return n;
}

int insert_graph_link (Graph *mygraph, int source, int target) {
  Node* sourceNode = mygraph->table[source];
  Node* targetNode = mygraph->table[target];
  sourceNode->outdegree++;
  targetNode->indegree++;
  addToList(sourceNode,target);
  return source;
}

int read_graph (Graph *mygraph, char *filename)
/* 
 * Reads in graph from FILE *filename which is of .gx format.
 * Stores it as Graph in *mygraph. 
 * Returns an error if file does not start with MAX command,
 * or if any subsequent line is not a NODE or EDGE command. 
 * Does not check that node numbers do not exceed the maximum number
 * Defined by the MAX command. 
 * 8/2/2010 - JLS
 */
{
  FILE *fp;
  char command[80], name[80];
  int i, s, t;
  fp= fopen (filename, "r");
  if (fp==NULL) {
    fprintf(stderr,"cannot open file %s\n", filename);
    return -1;
  }
  printf ("Reading graph from %s\n", filename);
  fscanf (fp,"%s", command);
  if (strcmp (command, "MAX")!=0) {
    fprintf (stderr, "Error in graphics file format\n");
    return -1;
  } else {
    fscanf (fp, "%d", &i);
    initialize_graph (mygraph, i+1); // +1 so nodes can be numbered 1..MAX
    while (fscanf (fp, "%s", command)!=EOF) {
      if (strcmp (command, "NODE")==0) {
        fscanf (fp, "%d %s", &i, name);
        insert_graph_node (mygraph, i, name);
      } else if (strcmp (command, "EDGE")==0) {
        fscanf (fp, "%d %d", &s, &t);
        insert_graph_link (mygraph, s, t);
      } else return -1;
    }
  }
  return 0;
}
void print_graph (Graph *mygraph)
/* 
 * Prints out Graph *mygraph to the stdout in .gx format.
 * 8/2/2010 - JLS
 */
{
  int i;
  List *current;
  printf ("MAX %d\n", mygraph->MaxSize);
  for (i=0; i<mygraph->MaxSize; i++)
    if (mygraph->table[i]->name!=NULL) {
      printf ("NODE %d %s\n", i, mygraph->table[i]->name);
      current= mygraph->table[i]->outlist;
      while (current!=NULL) {
        printf ("EDGE %d %d\n", i, current->index);
        current= current->next;
      }
    }
}
