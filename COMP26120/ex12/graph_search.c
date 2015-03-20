#include "graph.h"
#include <stdbool.h>
/* Good luck */

typedef struct queue{
  int max;
  int head;
  int tail;
  int items;
  Node** list;
}Queue;

void init_queue(Queue* q,int max){
  q->max = max;
  q->head = 0;
  q->tail = 0;
  q->items = 0;
  q->list = (Node**)malloc(sizeof(Node**));
}

bool has_items(Queue* q){return q->items>0;}

Node* pop_head(Queue* q){
  if(q->items==0)exit(1);
  q->items--;
  return q->list[q->head++];
}

bool item_in_queue(Queue* q,Node* item){
  int i;
  for(i=0;i<q->tail;i++){
    if(q->list[i]==item) return true;
  }
  return false;
}

void append_item(Queue* q,Node* item){
  if(item_in_queue(q,item)) return;
  q->items++;
  q->list[q->tail++] = item;
}

void bfs(Graph* g,Node* start, void function(Node*)){
  Queue q;
  init_queue(&q, g->MaxSize);
  append_item(&q, start);
  while(has_items(&q)){
    Node* head = pop_head(&q);
    function(head);
    if(head->outlist!=NULL){
      Node* next = g->table[head->outlist->index];
      append_item(&q, next);
      List* curentList = head->outlist;
      while(curentList!=NULL){
        next = g->table[curentList->index];
        curentList=curentList->next;
        append_item(&q, next);
      }
    }
  }
  return;
}

void dfs_i(Graph* g, Queue* q,Node* start, void function(Node*)){
  // if item in queue then we are done
  if(item_in_queue(q, start)) return;
  // else call function
  function(start);
  // then add item to processed list
  append_item(q, start);
  // if has child items...
  if(start->outlist!=NULL){
    Node* next = g->table[start->outlist->index];
    // call dfs recursive
    dfs_i(g, q, next, function);
    List* curentList = start->outlist;
    // loop through the rest in the list
    while(curentList!=NULL){
      next = g->table[curentList->index];
      curentList=curentList->next;
      dfs_i(g, q, next, function);
    }
  }
}

void dfs(Graph* g, Node* start, void function(Node*)){
  Queue q;
  init_queue(&q, g->MaxSize);
  dfs_i(g, &q, start, function);
}
