#include <stdio.h>

/* these arrays are just used to give the parameters to 'insert',
   to create the 'people' array */
#define HOW_MANY 7
char *names[HOW_MANY]= {"Simon", "Suzie", "Alfred", "Chip", "John", "Tim",
		      "Harriet"};
int ages[HOW_MANY]= {22, 24, 106, 6, 18, 32, 24};

/* declare your struct for a person here */
typedef struct {
	char *name,
	int age
} Person

static void insert (/* array parameter */, char *name, int age) {
  static int nextfreeplace = 0;i
  /* put name and age into the next free place in the array parameter here */
  /* modify nextfreeplace here */
}

int main(int argc, char **argv) {

  /* declare the people array here */

  for (... < HOW_MANY ...) {
    insert (people, names[...], ages[...]);
  }

  /* print the people array here*/

  return 0;
}
