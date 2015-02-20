

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#define __USE_BSD
#include <string.h>

#include "speller.h"
#include "dict.h"

#define HASH_MODE_INCR   0
#define HASH_MODE_POLY   1
#define HASH_MODE_DOUBLE 2

#define MAXSKIP 10

typedef uint32_t uint;

typedef struct { // hash-table entry
  Key_Type element; // only data is the key itself
  enum {empty, in_use, deleted} state;
} cell;

typedef unsigned int Table_size; // type for size-of or index-into hash table

struct table {
  cell *cells; Table_size table_size; // cell cells [table_size];
  Table_size num_entries; // number of cells in_use
  uint collisions;
  // add anything else that you need
};

uint hash (Key_Type word){
  uint hash = 5381;
  int c = word[0];
  for(int i = 0;c!='\0';i++,c=word[i])
    hash = ((hash << 5) + hash) ^ c;
  return hash;
}

/**
 * similar to the above with different initial conditions and shift factor
 */
uint hash2 (Key_Type word){
  uint hash = 7877;
  int c = word[0];
  for(int i = 0;c!='\0';i++,c=word[i])
    hash = ((hash << 8) + hash) ^ c;
  return hash;
}


Table initialize_table(Table_size size) {
  Table table = (Table) malloc(sizeof(struct table));
  check(table);
  table->cells = malloc(sizeof(cell)*size);
  for(uint i = 0;i<size;i++){
    cell* c = &table->cells[i];
    c->state = empty;
  }
  check(table->cells);
  table->table_size = size;
  table->collisions = 0;
  table->num_entries = 0;
  return table;
}


int cmp(Key_Type a, Key_Type b){
  return strcmp(a,b);
}

int polyHash(hashvalue,attempts){
  return hashvalue + (attempts << attempts / 2);
}

int doubleHash(hashvalue,otherhash,attempts){
  return hashvalue + otherhash * attempts;
}

Table insert (Key_Type newKey, Table table) {
  uint hashvalue = hash(newKey);
  uint otherhash;
  if (mode==HASH_MODE_DOUBLE)
    otherhash = hash2(newKey);

  uint position = hashvalue % table->table_size;
  cell* initialLocation = &table->cells[position];
  switch(initialLocation->state){
    case empty:
      initialLocation->state = in_use;
      initialLocation->element = strdup(newKey);
      break;
    case in_use:
      // if the key is already in the table then we are done
      if(cmp(initialLocation->element,newKey)==0) return table;
      table->collisions++;

    case deleted:
      for(int attempts = 0;attempts<MAXSKIP;attempts++){
        // try and find the element in the next MAXSKIP elements

        if(mode==HASH_MODE_INCR)
          position = (position+1) % table->table_size;
        else if (mode==HASH_MODE_POLY)
          position = polyHash(hashvalue,attempts) % table->table_size;
        else if (mode==HASH_MODE_DOUBLE)
          position = doubleHash(hashvalue,otherhash,attempts) % table->table_size;

        cell* curentLocation = &table->cells[position];
        if(curentLocation->state == empty){
          curentLocation->state = in_use;
          curentLocation->element = strdup(newKey);
          return table; // element inserted in hashtable
        }else if(curentLocation->state==in_use && cmp(curentLocation->element,newKey)==0){
          return table; // element found no need to insert again
        }else{
          // key is deleted skip next to next
        }
      }
      // if we get here the tree is full for this location we need to rehash
      printf("Error hash collision: no more space in hash table for new key: %s\n",newKey);
      //exit(0);
      break;
    default:
      return NULL; // error
      break;
  }
  table->num_entries++;
  return table;
}

Boolean find (Key_Type key, Table table) {
  uint hashvalue = hash(key);
  uint position = hashvalue % table->table_size;
  cell* initialLocation = &table->cells[position];
  if(initialLocation->state == in_use && cmp(initialLocation->element,key)==0)
    return TRUE;
  else{
    for(uint attempt = 0;attempt<MAXSKIP;attempt++){
      position = (position+1) % table->table_size;
      cell* curentLocation = &table->cells[position];
      if(curentLocation->state == empty){
        return FALSE; // cell is empty stop looking
      }else if(curentLocation->state == in_use && cmp(curentLocation->element,key)==0){
        return TRUE; // found element that is equal so we found it return TRUE;
      }
      // if not found or not empty then try next location
    }
    // if here then not found in all places
    return FALSE;
  }
}

void print_table (Table table) {
  for(int i = 0;i<table->table_size;i++){
    if((table->cells[i]).state==in_use){
      printf("Hash entry at [%d] for hash value %d : %s\n",i,hash(table->cells[i].element),table->cells[i].element);
    }
  }
}

void print_stats (Table table) {
  printf("Number of entries: %d\n",table->num_entries);
  printf("Total size of hashmap: %d\n",table->table_size);
  printf("Total insert collisions: %d\n",table->collisions);
}
