#include "graph.h"

void printNode(Node* node);
void updateMaxOutDeg(Node *);

Node* bfsmaxoutdeg;

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

  printf("Min out degree is...");
  printNode(minoutdeg);

  bfs(&mygraph, minoutdeg, updateMaxOutDeg);

  printf("Maximum out degree reached from min out degree is...");
  printNode(bfsmaxoutdeg);

  return(0);
}

void printNode(Node* node){
  printf("Node(%s)\n\n",node->name);
}
void updateMaxOutDeg(Node* node){
  if(bfsmaxoutdeg==NULL) bfsmaxoutdeg = node;
  else if(bfsmaxoutdeg->outdegree>node->outdegree) bfsmaxoutdeg = node;
}
