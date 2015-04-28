//
// Created by James Peach on 17/04/15.
//

#ifndef EX13_ORDERED_QUEUE_H
#define EX13_ORDERED_QUEUE_H

#include <stdbool.h>

typedef int Data;

typedef struct Item {
    int priority;
    Data data;
    int uid;
} Item;

typedef struct Queue {
    int maxSize;
    int nextIndex;
    int nextUid;
    Item* array;
} Queue;

extern void queue_init(Queue* q,int maxSize);
extern int queue_add(Queue* q, Data data, int priority);
extern Data queue_pop(Queue* q);
extern void queue_alter(Queue* q, int id, int priority);
extern int queue_size(Queue* q);

Item new_item(Queue* q, Data data,int priority);
void bubble_up(Queue* q,int bubbleThis);
void bubble_down(Queue* q,int bubbleThis);
int parentId(int i);
int leftId(int i);
int rightId(int i);
void swap(Queue* q,int this, int that);

#endif //EX13_ORDERED_QUEUE_H
