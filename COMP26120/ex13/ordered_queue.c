//
// Created by James Peach on 17/04/15.
//

#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
#include "ordered_queue.h"

void queue_init(Queue *q, int maxSize) {
    q->maxSize = maxSize;
    q->nextIndex = 1;
    q->array = (Item*)malloc(sizeof(Item) * maxSize);
}

int queue_add(Queue *q, void *data, int priority) {
    int nextIndex = q->nextIndex;
    if(q->nextIndex>q->maxSize) {
        perror("Queue overflow");
        exit(1);
    }
    q->array[nextIndex] = new_item(data,priority);
    q->nextIndex = nextIndex+1;
    bubble_up(q,nextIndex);
    return nextIndex;
}

Item new_item(void *data, int priority) {
    Item i;
    i.priority = priority;
    i.data = data;
    return i;
}

void bubble_up(Queue* q,int bubbleThis){
    Item this = q->array[bubbleThis];
    int pid = parentId(bubbleThis);

    // base case root el
    if(pid==0) return;
    Item parent = q->array[pid];
    // if parent is larger prior then swap
    if(this.priority<parent.priority){
        swap(q,bubbleThis, pid);
        bubble_up(q, pid);
    }
}

void bubble_down(Queue *q, int bubbleThis) {
    // base case this is the largest element
    if(q->nextIndex <= leftId(bubbleThis))
        return;
    // find the smallest priority child and swap this with it
    // we know we have at least a left child so that is the curent min
    int minChildId = leftId(bubbleThis);

    // calculate the right child id (if there is one)
    int rid = rightId(bubbleThis);
    // if there is a right child and the priority of it is less than our curent min
    if(q->nextIndex > rid && q->array[rid].priority < q->array[minChildId].priority)
        // then this is our new min child
        minChildId = rid;

    // if our smallest child is a lower priority then swap
    if(q->array[bubbleThis].priority>q->array[minChildId].priority) {
        swap(q, bubbleThis, minChildId);
        bubble_down(q, minChildId);
    }
}

void *queue_pop(Queue *q) {
    Item top = q->array[1];
    q->nextIndex--;
    // if there are more item in the queue swap this with the last item and bubble it down
    if(q->nextIndex>1) {
        swap(q, 1, q->nextIndex);
        bubble_down(q, 1);
    }
    return top.data;
}

int parentId(int i) { return (i)>>1; }

int leftId(int i) { return (i)<<1; }

int rightId(int i) { return ((i)<<1)+1; }

void swap(Queue *q, int this, int that) {
    Item temp = q->array[that];
    q->array[that]=q->array[this];
    q->array[this]=temp;
}

int queue_size(Queue *q) {
    return q->nextIndex-1;
}

