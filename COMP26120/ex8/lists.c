#include <stdio.h>
#include <stdlib.h>

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
 * insert. inserts a person into the head of the list
 * params
 * list - a pointer to the first item pointer
 * name - the person name
 * age - the person age
 */
void insert (Person** list, char *name, int age) {
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
    insert(&people, names[i], ages[i]);
  }
  
  printList(people);
  freeList(people);

  return 0;
}
