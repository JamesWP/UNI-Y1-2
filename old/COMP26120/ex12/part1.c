#include "graph.h"

void printNode(Node* node);

int main(int argc,char *argv[])
{
  Graph mygraph;

  read_graph(&mygraph,argv[1]);

  Node *minindeg = NULL,*maxindeg = NULL;
  Node *minoutdeg = NULL,*maxoutdeg = NULL;

  for(int i=1;i<mygraph.MaxSize;i++){
    if((minindeg == NULL || minindeg->indegree>mygraph.table[i]->indegree) && mygraph.table[i]->indegree != 0) minindeg = mygraph.table[i];
    if( maxindeg == NULL || maxindeg->indegree<mygraph.table[i]->indegree) maxindeg = mygraph.table[i];

    if((minoutdeg == NULL || minoutdeg->outdegree>mygraph.table[i]->outdegree) && mygraph.table[i]->outdegree != 0) minoutdeg = mygraph.table[i];
    if( maxoutdeg == NULL || maxoutdeg->outdegree<mygraph.table[i]->outdegree) maxoutdeg = mygraph.table[i];
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
  printf("Node(%s) od:%d ,id: %d\n\n",node->name,node->outdegree,node->indegree);
}
