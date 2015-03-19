#include "graph.h"

void printNode(Node* node);

int main(int argc,char *argv[])
{
  Graph mygraph;

  read_graph(&mygraph,argv[1]);

  Node *minindeg = mygraph.table[1],*maxindeg = mygraph.table[1];
  Node *minoutdeg = mygraph.table[1],*maxoutdeg = mygraph.table[1];

  for(int i=1;i<mygraph.MaxSize;i++){
    if(minindeg->indegree>mygraph.table[i]->indegree) minindeg = mygraph.table[i];
    if(maxindeg->indegree<mygraph.table[i]->indegree) maxindeg = mygraph.table[i];

    if(minoutdeg->outdegree>mygraph.table[i]->outdegree) minoutdeg = mygraph.table[i];
    if(maxoutdeg->outdegree<mygraph.table[i]->outdegree) maxoutdeg = mygraph.table[i];
  }

  printf("min in degree\n");
  printNode(minindeg);

  printf("max in degree\n");
  printNode(maxindeg);

  printf("min out degree\n");
  printNode(minoutdeg);

  printf("max out degree\n");
  printNode(maxoutdeg);

  return(0);
}

void printNode(Node* node){
  printf("Node(%s)\n\n",node->name);
}
