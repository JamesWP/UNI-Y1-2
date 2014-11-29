#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* declare your struct for a person here */
typedef enum staff_or_student_enum {
  TYPE_NEITHER,TYPE_STAFF,TYPE_STUDENT
} Person_type;


typedef struct person {
	char *name;
	int age;
  Person_type type;
  union {
    char *programme_name;
    char *room_number;
  };
  struct person *next;
} Person;


/* these arrays are just used to give the parameters to 'insert',
   to create the 'people' array */
#define HOW_MANY 7
char *names[HOW_MANY]= {"Simon", "Suzie", "Alfred", "Chip", "John", "Tim",
          "Harriet"};
int ages[HOW_MANY]= {22, 24, 106, 6, 18, 32, 24};
Person_type types[HOW_MANY]= {TYPE_NEITHER,TYPE_STAFF,TYPE_STUDENT,
          TYPE_STAFF,TYPE_STUDENT,TYPE_NEITHER,TYPE_STAFF};
char *data[HOW_MANY] = {NULL,"Kilburn 2.1.1","CS","Kilburn 2.1.2","CSWIE",NULL,"Kilburn 2.1.3"};
/**
 * insert_start. inserts a person into the head of the list
 * params
 * list - a pointer to the first item pointer
 * name - the person name
 * age - the person age
 */
void insert_start (Person** list, char *name, int age, Person_type type, char* data) {
  Person* newPerson =  (Person *)malloc(sizeof(Person));
  if(newPerson == NULL) {
    fprintf(stderr,"Malloc failed");
    exit(1);
  }
  newPerson->name = name;
  newPerson->age = age;
  newPerson->next = *list;
  newPerson->type = type;
  switch (type){
    case TYPE_STAFF:
      newPerson->room_number = data;
      break;
    case TYPE_STUDENT:
      newPerson->programme_name = data;
      break;
    default: break;
  }
  *list = newPerson;
}

/**
 * insert_end. inserts a person into the head of the list
 * params
 * list - a pointer to the first item pointer
 * name - the person name
 * age - the person age
 */
void insert_end (Person** list, char *name, int age, Person_type type, char* data) {
  Person* newPerson =  (Person *)malloc(sizeof(Person));
  if(newPerson == NULL) {
    fprintf(stderr,"Malloc failed");
    exit(1);
  }
  newPerson->name = name;
  newPerson->age = age;
  newPerson->next = NULL;
  newPerson->type = type;
  switch (type){
    case TYPE_STAFF:
      newPerson->room_number = data;
      break;
    case TYPE_STUDENT:
      newPerson->programme_name = data;
      break;
    default: break;
  }

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

int compare_people_by_name(Person *a, Person *b){
  return strcmp(a->name,b->name);
}

int compare_people_by_age(Person *a, Person *b){
  return a->age - b->age;
}


/**
 * insert_sorted. inserts an item into the list in the correct pos
 * according to the comparison {compare people}
 */
void insert_sorted(Person** list, char *name, int age, Person_type type, char* data,
                    int (*compare_people)(Person* a, Person* b)){
  Person* newPerson =  (Person *)malloc(sizeof(Person));
  if(newPerson == NULL) {
    fprintf(stderr,"Malloc failed");
    exit(1);
  }
  newPerson->name = name;
  newPerson->age = age;
  newPerson->type = type;
  switch (type){
    case TYPE_STAFF:
      newPerson->room_number = data;
      break;
    case TYPE_STUDENT:
      newPerson->programme_name = data;
      break;
    default: break;
  }

  if (*list==NULL || compare_people(newPerson,*list) < 0){
    free(newPerson);
    insert_start(list,name,age,type,data);
  }else{
    Person* traveler = *list;
    // travel down the list until we are at the insert place
    while(traveler->next != NULL
    && compare_people(newPerson, traveler->next) > 0)
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
    insert_start(&people, names[i], ages[i], types[i], data[i] );
  }

  printf("Insert start:");
  printList(people);
  freeList(people);
  printf("\n");

  people = NULL;
  count = 0;
  for (int i = 0;i < HOW_MANY; i++) {
    insert_end(&people, names[i], ages[i], types[i], data[i]);
  }

  printf("Insert end:");
  printList(people);
  freeList(people);
  printf("\n");

  people = NULL;
  count = 0;
  for (int i = 0;i < HOW_MANY; i++) {
    insert_sorted(&people, names[i], ages[i], types[i], data[i], compare_people_by_name);
  }

  printf("Insert sorted name:");
  printList(people);
  freeList(people);
  printf("\n");

  people = NULL;
  count = 0;
  for (int i = 0;i < HOW_MANY; i++) {
    insert_sorted(&people, names[i], ages[i], types[i], data[i], compare_people_by_age);
  }

  printf("Insert sorted age:");
  printList(people);
  freeList(people);

  return 0;
}
