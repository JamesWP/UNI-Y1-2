#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* these arrays are just used to give the parameters to 'insert',
   to create the 'people' array */
#define HOW_MANY 7
char *names[HOW_MANY]= {"Simon", "Suzie", "Alfred", "Chip", "John", "Tim",
          "Harriet"};
int ages[HOW_MANY]= {22, 24, 106, 6, 18, 32, 24};

/* declare your struct for a person here */
typedef struct person {
	char *name;
	int age;
  struct person *next;
} Person;

/**
 * insert_start. inserts a person into the head of the list
 * params
 * list - a pointer to the first item pointer
 * name - the person name
 * age - the person age
 */
void insert_start (Person** list, char *name, int age) {
  Person* newPerson =  (Person *)malloc(sizeof(Person));
  if(newPerson == NULL) {
    fprintf(stderr,"Malloc failed");
    exit(1);
  }
  newPerson->name = name;
  newPerson->age = age;
  newPerson->next = *list;
  *list = newPerson;
}

/**
 * insert_end. inserts a person into the head of the list
 * params
 * list - a pointer to the first item pointer
 * name - the person name
 * age - the person age
 */
void insert_end (Person** list, char *name, int age) {
  Person* newPerson =  (Person *)malloc(sizeof(Person));
  if(newPerson == NULL) {
    fprintf(stderr,"Malloc failed");
    exit(1);
  }
  newPerson->name = name;
  newPerson->age = age;
  newPerson->next = NULL;
 
  // travel down the list to the end
  Person* traveler = *list;
  while(traveler !=NULL && traveler->next != NULL)
    traveler = traveler->next;
  // if you have the end add the new to the end
  if(traveler!=NULL)
    traveler->next = newPerson;
  else
    *list = newPerson;
}

int compare_people(Person *a, Person *b){
  return strcmp(a->name,b->name);
}

void insert_sorted(Person** list, char *name, int age){
  Person* newPerson =  (Person *)malloc(sizeof(Person));
  if(newPerson == NULL) {
    fprintf(stderr,"Malloc failed");
    exit(1);
  }
  newPerson->name = name;
  newPerson->age = age;
 
  if (*list==NULL)
    insert_start(list,name,age);
  else{
    Person* traveler = *list;
    // travel down the list until we are at the insert place
    while( traveler->next != NULL 
      && compare_people(newPerson, traveler->next) < 0)
      traveler = traveler->next;
    newPerson->next = traveler->next;
    traveler->next = newPerson;
  }
}

/**
 * printList
 * prints all the list items in the list pointed to by list
 */
void printList(Person* list){
  while (list!=NULL){
    Person* tmp = list;
    list = list->next;
    printf("Person(name:%s,age:%d)\n",tmp->name,tmp->age);
  }
}
/**
 * freeList
 * frees all the list items in the list pointed to by list
 */
void freeList(Person* list){
  while (list!=NULL){
    Person* tmp = list;
    list = list->next;
    free(tmp);
  }
}

int main(int argc, char **argv) {
  Person *people = NULL;
  int count = 0;
  for (int i = 0;i < HOW_MANY; i++) {
    insert_sorted(&people, names[i], ages[i]);
  }
  
  printList(people);
  freeList(people);

  return 0;
}
