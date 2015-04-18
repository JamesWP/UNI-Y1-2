//
// Created by James Peach on 17/04/15.
//

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "ordered_queue.h"

#define max_queue 200

int main(int argv, char *argc[]) {
    printf("Hello world\n");

    srand (time(NULL));;

    Queue q;
    queue_init(&q,max_queue);

    for(int i;i<max_queue;i++){
        int randNo = rand()%99+1;
        queue_add(&q,randNo,randNo);
    }

    int last = -1;
    while(queue_size(&q)>0) {
        int d = (int) queue_pop(&q);
        if (last != -1 && last > d) {
            perror("Out of order");
            exit(1);
        }
        last = d;
        printf("Poped: %x\n", d);
    }
    return 0;
}
