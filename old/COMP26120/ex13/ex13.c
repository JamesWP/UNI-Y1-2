//
// Created by James Peach on 17/04/15.
//

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "ordered_queue.h"

#include "ex13.h"

#define max_queue 1000000

void testQueueOrder(Queue *q);

int main(int argc, char *argv[]) {
    test1();
    test2();
    return 0;
}

void test1() {
    srand (time(NULL));

    Queue q;
    queue_init(&q,max_queue);

#pragma GCC diagnostic push
#pragma GCC diagnostic ignored "-Wint-to-void-pointer-cast"
    for(int i=0;i<max_queue;i++){
        int randNo = rand()%99+1;
        queue_add(&q, randNo, randNo);
    }
#pragma GCC diagnostic pop
    testQueueOrder(&q);
}

void testQueueOrder(Queue *q) {
    int last = -1;
    while(queue_size(q)>0) {
        int d = queue_pop(q);
        //printf("Poped: %x\n", d);
        if (last != -1 && last > d) {
            perror("Out of order");
            exit(1);
        }
        last = d;
    }
    printf("Test passed\n");
}

void test2(){
    // test adding items then altering priories then poping
    Queue q;
    queue_init(&q,6);

    int itemHandle1 = queue_add(&q,0x8,20);
    queue_add(&q,0x21,21);
    queue_add(&q,0x22,22);
    queue_add(&q,0x23,23);
    queue_add(&q,0x24,24);
    int itemHandle2 = queue_add(&q,0x10,25);

    queue_alter(&q,itemHandle2,10);
    queue_alter(&q,itemHandle1,8);
    testQueueOrder(&q);
}