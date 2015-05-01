//
// Created by James Peach on 18/04/15.
//

#include <limits.h>
#include "dijkstra.h"
#include "ordered_queue.h"
#include "graph.h"

#define UNDEFINED 0

float calculateAverage(uint *numbers, int items);


int *reconstructPath(int *cameFrom, int source, int destination, int i);

uint heuristic(Graph *g, int i);

int main(int argc, char *argv[]) {
    Graph g;
    read_graph(&g,argv[1]);

    if(argc > 2 && strcmp(argv[2],"smallworld")==0){
        printf("smallworld");
        float total = 0.0f;
        for(int i=0;i<=g.maxID;i++){
            uint* dist = dijkstra(&g,i);
            float average = calculateAverage(dist,g.maxID);
            total += average;
            free(dist);
        }
        printf("\nsmallword number %f\n",total/g.maxID);
    }else if(argc > 2 && strcmp(argv[2],"astar")==0){
        int source = atoi(argv[3]);
        int destination = atoi(argv[4]);
        int* path = astar(&g,source,destination);
        if(path==NULL){
            perror("No path found");
            exit(12);
        }
        int startIndex = path[0];
        for(;startIndex>0;startIndex--){
            printf("Path %d\n",path[startIndex]);
        }
        free(path);

    }else{
        uint* dist = dijkstra(&g,1);

        printf("Printing out island nodes:\n");
        for(int i=0;i<=g.maxID;i++){
            if(dist[i]==INT_MAX)
                printf("Island node at index %d\n",i);
        }
        free(dist);
    }

    return 0;
}

float calculateAverage(uint *numbers, int items) {
    uint sum = 0;
    for(int i=0;i<=items;i++){
        if(numbers[i]<INT_MAX && numbers[i]>0){
            sum += (uint)numbers[i];
        }
    }
    return sum / (float) items;
}


uint* dijkstra(Graph *g, int source) {
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
    for(int i=1;i<=g->maxID;i++){
        if(g->table[i]!=NULL){
            int v = i;
    //:4
            if(v!=source){
                dist[v]=INT_MAX;
                prev[v]=UNDEFINED;
            }else {
                qid[v] = queue_add(q,v,dist[v]);
            }
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
                qid[v] = queue_add(q,v,dist[v]);
            }
            neighbours = neighbours->next;
        }
    }

    free(q);
    free(qid);
    free(prev);
    return dist;
}

#define MAXOD 1
uint heuristic(Graph *g, int node) {
    return 1;
    /*
    int od = g->table[node]->outdegree;
    return od>MAXOD?0:MAXOD-od;
     */
}


int *astar(Graph *g, int source, int destination) {
    Queue* q = (Queue*) malloc(sizeof(Queue));
    int* qid = (int*) malloc(sizeof(int)* g->maxID);
    uint* dist = (uint*) malloc(sizeof(uint)* g->maxID);
    int* prev = (int*) malloc(sizeof(int)* g->maxID);
    queue_init(q,g->maxID);

    //:2
    dist[source] = 0;
    //:3
    for(int i=1;i<=g->maxID;i++){
        if(g->table[i]!=NULL){
            int v = i;
            //:4
            if(v!=source){
                dist[v]=INT_MAX;
                prev[v]=UNDEFINED;
            }else {
                qid[v] = queue_add(q,v,dist[v] + heuristic(g, v));
            }
        }
    }

    //:11
    while (queue_size(q)>0){
        int u = queue_pop(q);
        if(u==destination){
            free(q);
            free(qid);
            free(prev);
            return reconstructPath(prev, source, destination, g->maxID);
        }
        List* neighbours = g->table[u]->outlist;
        while(neighbours!=NULL){
            int v = neighbours->index;
            int alt = dist[u] + 1/*length(u, v)*/; // length of edge between node and neighbour is 1
            if(alt<dist[v]){
                dist[v]=alt;
                prev[v]=u;
                qid[v] = queue_add(q,v,dist[v]);
            }
            neighbours = neighbours->next;
        }
    }

    free(q);
    free(qid);
    free(prev);

    return NULL;
}

int *reconstructPath(int *cameFrom, int source, int destination, int maxSteps) {
    int* path = (int*)malloc(sizeof(int)*maxSteps);
    for(int i=0;i<maxSteps;i++)path[i]=0;
    int pathIndex=1;
    int current = destination;
    printf("current %d\n",current);
    while(pathIndex<maxSteps&&current!=source){
        path[pathIndex++] = current;
        current = cameFrom[current];
        printf("current %d\n",current);
        getchar();
    }
    path[pathIndex] = current;
    path[0] = pathIndex; // first index contains where to start from (and work backwards to 0) for path
    return path;
}
