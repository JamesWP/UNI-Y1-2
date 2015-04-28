//
// Created by James Peach on 18/04/15.
//

#include <limits.h>
#include "dijkstra.h"
#include "ordered_queue.h"

#define UNDEFINED 0

int main(int argv, char *argc[]) {

    return 0;
}


float dijkstra(Graph *g, int source) {
/*
    1    function Dijkstra(Graph, source):
    2      dist[source] ← 0                            // Initialization
    3      for each vertex v in Graph:
    4          if v ≠ source
    5              dist[v] ← infinity                  // Unknown distance from source to v
    6              prev[v] ← undefined                 // Predecessor of v
    7          end if
    8          Q.add_with_priority(v, dist[v])
    9      end for
    10
    11     while Q is not empty:                       // The main loop
    12         u ← Q.extract_min()                     // Remove and return best vertex
    13         for each neighbor v of u:
    14             alt = dist[u] + length(u, v)
    15             if alt < dist[v]
    16                 dist[v] ← alt
    17                 prev[v] ← u
    18                 Q.decrease_priority(v, alt)
    19             end if
    20         end for
    21     end while
    21     return dist[], prev[]
    22   end function
*/
    Queue* q = (Queue*) malloc(sizeof(Queue));
    uint* qid = (uint*) malloc(sizeof(uint)* g->maxID);
    uint* dist = (uint*) malloc(sizeof(uint)* g->maxID);
    uint* prev = (uint*) malloc(sizeof(uint)* g->maxID);
    queue_init(q,g->maxID);


    //:2
    dist[source] = 0;
    //:3
    for(int i=0;i<g->maxID;i++){
        if(g->table[i]!=NULL){
            int v = i;
    //:4
            if(v!=source){
                dist[v]=UINT_MAX;
                prev[v]=UNDEFINED;
            }
            qid[v] = queue_add(q,v,dist[v]);
        }
    }
    //:11
    while (queue_size(q)>0){
        int u = queue_pop(q);
        List* neighbours = g->table[u]->outlist;
        while(neighbours!=NULL){
            int v = neighbours->index;
            int alt = dist[u] + 1/*length(u, v)*/; // length of edge between node and neighbour is 1
            if(alt<dist[v]){
                dist[v]=alt;
                prev[v]=u;
                queue_alter(q,qid[v],alt);
            }
            neighbours = neighbours->next;
        }
    }

    free(q);
    free(qid);
    free(dist);
    free(prev);
    return 0;
}
