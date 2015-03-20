#include "graph.h"
#include <stdbool.h>

void printNode(Node* node);
void updateBfsMaxOutDeg(Node *);
void updateDfsMaxOutDeg(Node *);
bool queue_contains_all(Graph* g, Queue* q);

bool bfsismaxoutdegreachable = false;
bool dfsismaxoutdegreachable = false;
Node* bfsmaxoutdeg;
Node* dfsmaxoutdeg;

Node *minindeg = NULL,*maxindeg = NULL;
Node *minoutdeg = NULL,*maxoutdeg = NULL;

Queue bfsq,dfsq;

int main(int argc,char *argv[])
{
  Graph mygraph;

  read_graph(&mygraph,argv[1]);


  for(int i=1;i<mygraph.MaxSize;i++){
    if((minindeg == NULL || minindeg->indegree>mygraph.table[i]->indegree) && mygraph.table[i]->indegree != 0) minindeg = mygraph.table[i];
    if( maxindeg == NULL || maxindeg->indegree<mygraph.table[i]->indegree) maxindeg = mygraph.table[i];

    if((minoutdeg == NULL || minoutdeg->outdegree>mygraph.table[i]->outdegree) && mygraph.table[i]->outdegree != 0) minoutdeg = mygraph.table[i];
    if( maxoutdeg == NULL || maxoutdeg->outdegree<mygraph.table[i]->outdegree) maxoutdeg = mygraph.table[i];
  }

  printf("Min out degree is...");
  printNode(minoutdeg);


  init_queue(&bfsq, mygraph.MaxSize);
  init_queue(&dfsq, mygraph.MaxSize);

  bfs(&mygraph, minoutdeg, updateBfsMaxOutDeg);
  if(bfsismaxoutdegreachable)
    printf("Max out degree is reachable via bfs\n");
  else
    printf("Max out degree is not reachable via bfs\n");

  if(queue_contains_all(&mygraph,&bfsq))
    printf("All reachable via bfs\n");
  else
    printf("Not all reachable via bfs\n");

  printf("BFS - Maximum out degree reached from min out degree is...");
  printNode(bfsmaxoutdeg);

  dfs(&mygraph, minoutdeg, updateDfsMaxOutDeg);
  if(dfsismaxoutdegreachable)
    printf("Max out degree is reachable via dfs\n");
  else
    printf("Max out degree is not reachable via dfs\n");

  if(queue_contains_all(&mygraph,&dfsq))
    printf("All reachable via dfs\n");
  else
    printf("Not all reachable via dfs\n");

  printf("DFS - Maximum out degree reached from min out degree is...");
  printNode(dfsmaxoutdeg);




  return(0);
}

void printNode(Node* node){
  printf("Node(%s) id: %d, od:%d\n\n",node->name,node->indegree,node->outdegree);
}

void updateBfsMaxOutDeg(Node* node){
  if(bfsmaxoutdeg==NULL) bfsmaxoutdeg = node;
  else if(bfsmaxoutdeg->outdegree<node->outdegree) bfsmaxoutdeg = node;

  if(node==maxoutdeg) bfsismaxoutdegreachable = true;
  append_item(&bfsq,node);
}

void updateDfsMaxOutDeg(Node* node){
  if(dfsmaxoutdeg==NULL) dfsmaxoutdeg = node;
  else if(dfsmaxoutdeg->outdegree<node->outdegree) dfsmaxoutdeg = node;

  if(node==maxoutdeg) dfsismaxoutdegreachable = true;
  append_item(&dfsq,node);
}

bool queue_contains_all(Graph* g, Queue* q){
  for(int i=1;i<g->MaxSize;i++){
    Node* item = g->table[i];
    if(!item_in_queue(q, item))
      return false;
  }
  return true;
}
