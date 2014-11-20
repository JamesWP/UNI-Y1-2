#include <stdio.h>
#include <stdlib.h>

/* these arrays are just used to give the parameters to 'insert',
   to create the 'people' array */
#define HOW_MANY 7
char *names[HOW_MANY]= {"Simon", "Suzie", "Alfred", "Chip", "John", "Tim",
          "Harriet"};
int ages[HOW_MANY]= {22, 24, 106, 6, 18, 32, 24};

/* declare your struct for a person here */
typedef struct {
	char *name;
	int age;
} Person;

void insert (Person *array[], int *count, char *name, int age) {
  /* put name and age into the next free place in the array parameter here */
  /* modify nextfreeplace here */
  Person* newPerson =  (Person *)malloc(sizeof(Person));
  if(newPerson == NULL) {
    fprintf(stderr,"Malloc failed");
    exit(1);
  }
  newPerson->name = name;
  newPerson->age = age;
  array[(*count)++] = newPerson;
}

int main(int argc, char **argv) {

  /* declare the people array here */
  Person *people[HOW_MANY];
  int count = 0;
  for (int i = 0;i < HOW_MANY; i++) {
    insert (people, &count, names[i], ages[i]);
  }

  for (int i = 0;i < HOW_MANY; i++) {
    Person* curent = people[i];
    printf("Name: %s is %d age\n",curent->name,curent->age);
  }

  for (int i = 0;i < HOW_MANY; i++) {
    free(people[i]);
  }

  return 0;
}
