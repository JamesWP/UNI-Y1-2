//
// Created by James Peach on 18/04/15.
//

#ifndef EX13_DIJKSTRA_H
#define EX13_DIJKSTRA_H

#include "graph.h"

typedef unsigned int uint;
/* returns the average distance to all other nodes form the specified node*/
uint * dijkstra(Graph *g, int source);
/* returns the path from the source to the destination
 * 0 terminated array */
int *astar(Graph *g, int source, int destination);

#endif //EX13_DIJKSTRA_H
