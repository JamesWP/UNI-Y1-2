#include "graph.h"

void printNode(Node* node);
void updateBfsMaxOutDeg(Node *);
void updateDfsMaxOutDeg(Node *);

Node* bfsmaxoutdeg;
Node* dfsmaxoutdeg;

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

  minoutdeg = mygraph.table[1];
  bfs(&mygraph, minoutdeg, updateBfsMaxOutDeg);

  printf("BFS - Maximum out degree reached from min out degree is...");
  printNode(bfsmaxoutdeg);

  dfs(&mygraph, minoutdeg, updateDfsMaxOutDeg);

  printf("DFS - Maximum out degree reached from min out degree is...");
  printNode(dfsmaxoutdeg);


  return(0);
}

void printNode(Node* node){
  printf("Node(%s)\n\n",node->name);
}
void updateBfsMaxOutDeg(Node* node){
  printf("--BFS - visit %s \n",node->name);
  if(bfsmaxoutdeg==NULL) bfsmaxoutdeg = node;
  else if(bfsmaxoutdeg->outdegree>node->outdegree) bfsmaxoutdeg = node;
}

void updateDfsMaxOutDeg(Node* node){
  printf("--DFS - visit %s \n",node->name);
  if(dfsmaxoutdeg==NULL) dfsmaxoutdeg = node;
  else if(dfsmaxoutdeg->outdegree>node->outdegree) dfsmaxoutdeg = node;
}
